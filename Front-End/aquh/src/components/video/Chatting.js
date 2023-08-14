import { OpenVidu } from "openvidu-browser";

import axios from "axios";
import https from "../../utils/https"
import React, { Component, useState, useEffect } from "react";
import classes from "./Chatting.module.css";
// import classes from "./ChattingRow.module.css";
import UserVideoComponent from "./UserVideoComponent";
import { json } from "react-router-dom";
import ChattingSection from "./ChattingSection"


// import { useRecoilValue } from "recoil";
// import { memberNicknameState } from "../../store/loginUserInfoState";

const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === "production" ? "" : "https://i9b108.p.ssafy.io/";

// function GetMemberNickname(){
//   const memberNickname = useRecoilValue(memberNicknameState);
//   return memberNickname;
// }

export default class Chatting extends Component {
  constructor(props) {
    super(props);

    // These properties are in the state's component in order to re-render the HTML whenever their values change
    this.state = {
      mySessionId: "1234",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      session: undefined,
      mainStreamManager: undefined, // Main video of the page. Will be the 'publisher' or one of the 'subscribers'
      publisher: undefined,
      subscribers: [],
    };

    this.createSession = this.createSession.bind(this);
    this.joinSession = this.joinSession.bind(this);
    this.leaveSession = this.leaveSession.bind(this);
    this.switchCamera = this.switchCamera.bind(this);
    this.handleChangeSessionId = this.handleChangeSessionId.bind(this);
    this.handleChangeUserName = this.handleChangeUserName.bind(this);
    this.handleMainVideoStream = this.handleMainVideoStream.bind(this);
    this.onbeforeunload = this.onbeforeunload.bind(this);
  }

  componentDidMount() {
    window.addEventListener("beforeunload", this.onbeforeunload);
  }

  componentWillUnmount() {
    window.removeEventListener("beforeunload", this.onbeforeunload);
  }

  onbeforeunload(event) {
    this.leaveSession();
  }

  handleChangeSessionId(e) {
    this.setState({
      mySessionId: e.target.value,
    });
  }

  handleChangeUserName(e) {
    this.setState({
      myUserName: e.target.value,
    });
  }

  handleMainVideoStream(stream) {
    if (this.state.mainStreamManager !== stream) {
      this.setState({
        mainStreamManager: stream,
      });
    }
  }

  deleteSubscriber(streamManager) {
    let subscribers = this.state.subscribers;
    let index = subscribers.indexOf(streamManager, 0);
    if (index > -1) {
      subscribers.splice(index, 1);
      this.setState({
        subscribers: subscribers,
      });
    }
  }

  createSession() {
    this.OV = new OpenVidu();
    this.setState(
      {
        session: this.OV.initSession(),
      },
      () => {
        var mySession = this.state.session;

        mySession.on("streamCreated", (event) => {
          var subscriber = mySession.subscribe(event.stream, undefined);
          var subscribers = this.state.subscribers;
          subscribers.push(subscriber);

          this.setState({
            subscribers: subscribers,
          });
        });

        mySession.on("streamDestroyed", (event) => {
          this.deleteSubscriber(event.stream.streamManager);
        });

        mySession.on("exception", (exception) => {
          console.warn(exception);
        });
        alert("asdf");

        this.postSession().then((token) => {
          mySession
            .connect(token, { clientData: this.state.myUserName })
            .then(async () => {
              let publisher = await this.OV.initPublisherAsync(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                resolution: "640x480", // The resolution of your video
                frameRate: 30, // The frame rate of your video
                insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                mirror: false, // Whether to mirror your local video or not
              });
              mySession.publish(publisher);
              var devices = await this.OV.getDevices();
              var videoDevices = devices.filter(
                (device) => device.kind === "videoinput"
              );
              var currentVideoDeviceId = publisher.stream
                .getMediaStream()
                .getVideoTracks()[0]
                .getSettings().deviceId;
              var currentVideoDevice = videoDevices.find(
                (device) => device.deviceId === currentVideoDeviceId
              );
              this.setState({
                currentVideoDevice: currentVideoDevice,
                mainStreamManager: publisher,
                publisher: publisher,
              });
            })
            .catch((error) => {
              console.log(
                "There was an error connecting to the session:",
                error.code,
                error.message
              );
            });
        });
      }
    );
  }

  joinSession() {
    // --- 1) Get an OpenVidu object ---

    this.OV = new OpenVidu();

    // --- 2) Init a session ---

    this.setState(
      {
        session: this.OV.initSession(),
      },
      () => {
        var mySession = this.state.session;

        // --- 3) Specify the actions when events take place in the session ---

        // On every new Stream received...
        mySession.on("streamCreated", (event) => {
          // Subscribe to the Stream to receive it. Second parameter is undefined
          // so OpenVidu doesn't create an HTML video by its own
          var subscriber = mySession.subscribe(event.stream, undefined);
          var subscribers = this.state.subscribers;
          subscribers.push(subscriber);

          // Update the state with the new subscribers
          this.setState({
            subscribers: subscribers,
          });
        });

        // On every Stream destroyed...
        mySession.on("streamDestroyed", (event) => {
          // Remove the stream from 'subscribers' array
          this.deleteSubscriber(event.stream.streamManager);
        });

        // On every asynchronous exception...
        mySession.on("exception", (exception) => {
          console.warn(exception);
        });

        // --- 4) Connect to the session with a valid user token ---

        // Get a token from the OpenVidu deployment
        // this.enterSession(this.state.mySessionId).then((token) => {
        this.putSession().then((token) => {
          // First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
          // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
          mySession
            .connect(token, { clientData: this.state.myUserName })
            .then(async () => {
              // --- 5) Get your own camera stream ---

              // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
              // element: we will manage it on our own) and with the desired properties
              let publisher = await this.OV.initPublisherAsync(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                resolution: "640x480", // The resolution of your video
                frameRate: 30, // The frame rate of your video
                insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                mirror: false, // Whether to mirror your local video or not
              });

              // --- 6) Publish your stream ---

              mySession.publish(publisher);

              // Obtain the current video device in use
              var devices = await this.OV.getDevices();
              var videoDevices = devices.filter(
                (device) => device.kind === "videoinput"
              );
              var currentVideoDeviceId = publisher.stream
                .getMediaStream()
                .getVideoTracks()[0]
                .getSettings().deviceId;
              var currentVideoDevice = videoDevices.find(
                (device) => device.deviceId === currentVideoDeviceId
              );

              // Set the main video in the page to display our webcam and store our Publisher
              this.setState({
                currentVideoDevice: currentVideoDevice,
                mainStreamManager: publisher,
                publisher: publisher,
              });
            })
            .catch((error) => {
              console.log(
                "There was an error connecting to the session:",
                error.code,
                error.message
              );
            });
        });
      }
    );
  }

  leaveSession() {
    // --- 7) Leave the session by calling 'disconnect' method over the Session object ---

    const mySession = this.state.session;

    if (mySession) {
      mySession.disconnect();
    }

    // Empty all properties...
    this.OV = null;
    this.setState({
      session: undefined,
      subscribers: [],
      mySessionId: "1234",
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      mainStreamManager: undefined,
      publisher: undefined,
    });
  }

  async switchCamera() {
    try {
      const devices = await this.OV.getDevices();
      var videoDevices = devices.filter(
        (device) => device.kind === "videoinput"
      );

      if (videoDevices && videoDevices.length > 1) {
        var newVideoDevice = videoDevices.filter(
          (device) => device.deviceId !== this.state.currentVideoDevice.deviceId
        );

        if (newVideoDevice.length > 0) {
          // Creating a new publisher with specific videoSource
          // In mobile devices the default and first camera is the front one
          var newPublisher = this.OV.initPublisher(undefined, {
            videoSource: newVideoDevice[0].deviceId,
            publishAudio: true,
            publishVideo: true,
            mirror: true,
          });

          //newPublisher.once("accessAllowed", () => {
          await this.state.session.unpublish(this.state.mainStreamManager);

          await this.state.session.publish(newPublisher);
          this.setState({
            currentVideoDevice: newVideoDevice[0],
            mainStreamManager: newPublisher,
            publisher: newPublisher,
          });
        }
      }
    } catch (e) {
      console.error(e);
    }
  }

  render() {
    const mySessionId = this.state.mySessionId;
    const myUserName = this.state.myUserName;

    return (
      <div className={classes.container}>
        {this.state.session === undefined ? (
          <div id='Input'>
            <div id='Create-dialog' className={classes.jumbotronVerticalCenter}>
              <h1> Create a video session </h1>
              <form className={classes.formGroup} onSubmit={this.createSession}>
                <p>
                  <label>Participant: </label>
                  <input
                    className={classes.formControl}
                    type='text'
                    id='userName'
                    value={myUserName}
                    onChange={this.handleChangeUserName}
                    required
                  />
                </p>
                <p>
                  <label> Session: </label>
                  <input
                    className={classes.formControl}
                    type='text'
                    id='sessionId'
                    value={mySessionId}
                    onChange={this.handleChangeSessionId}
                    required
                  />
                </p>
                <p className={classes.textCenter}>
                  <input
                    className={classes.controlBtn}
                    name='commit'
                    type='submit'
                    value='Create'
                  />
                </p>
              </form>
            </div>
            {/* -------------------------------------------------------------- */}
            <div id='join-dialog' className={classes.jumbotronVerticalCenter}>
              <h1> Join a video session </h1>
              <form className={classes.formGroup} onSubmit={this.joinSession}>
                <p>
                  <label>Participant: </label>
                  <input
                    className={classes.formControl}
                    type='text'
                    id='userName'
                    value={myUserName}
                    onChange={this.handleChangeUserName}
                    required
                  />
                </p>
                <p>
                  <label> Session: </label>
                  <input
                    className={classes.formControl}
                    type='text'
                    id='sessionId'
                    value={mySessionId}
                    onChange={this.handleChangeSessionId}
                    required
                  />
                </p>
                <p className={classes.textCenter}>
                  <input
                    className={classes.controlBtn}
                    name='commit'
                    type='submit'
                    value='Join'
                  />
                </p>
              </form>
            </div>
          </div>
        ) : null}

        {this.state.session !== undefined ? (
          <div className={classes.container}>
            <h1 className={classes.sessionTitle}>{mySessionId}</h1>
            <div className={classes.videoPage}>
              <div className={classes.sessionMain}>
                {/* TODO : 카메라 스위치 했을 때 내 캐릭터 보이기 -> 본인은 무조건 좌측 상단*/}
                {/* 나의 화면 =  */}
                <div className={classes.videoContainer}>
                  {this.state.publisher !== undefined ? (
                    <div className={classes.streamContainer}>
                      <UserVideoComponent
                        streamManager={this.state.publisher}
                      />
                    </div>
                  ) : null}
                  {/* 나 제외 들어온 사람들 보이는 화면 -> 5개로 만들기 */}
                  {this.state.subscribers.map((sub, i) => (
                    <div key={sub.id} className={classes.streamContainer}>
                      {/* <span>{sub.id}</span> */}
                      <UserVideoComponent streamManager={sub} />
                    </div>
                  ))}
                </div>
              </div>
              <div className={classes.sessionRight}>
                <div className={classes.sessionNav}>
                  <input
                    className={classes.controlBtn}
                    type='button'
                    onClick={this.leaveSession}
                    value='화면 종료하기'
                  />
                  <input
                    className={classes.switchCameraBtn}
                    type='button'
                    onClick={this.switchCamera}
                    value='카메라끄기'
                  />
                </div>
                <div className={classes.sessionChat}>bbbbbb<ChattingSection/></div>
              </div>
            </div>
          </div>
        ) : null}
      </div>
    );
  }

  /**
   * --------------------------------------------
   * GETTING A TOKEN FROM YOUR APPLICATION SERVER
   * --------------------------------------------
   * The methods below request the creation of a Session and a Token to
   * your application server. This keeps your OpenVidu deployment secure.
   *
   * In this sample code, there is no user control at all. Anybody could
   * access your application server endpoints! In a real production
   * environment, your application server must identify the user to allow
   * access to the endpoints.
   *
   * Visit https://docs.openvidu.io/en/stable/application-server to learn
   * more about the integration of OpenVidu in your application server.
   */
  
  async postSession() {
    // const sessionId = await this.createSession(this.state.mySessionId);
    console.log("this is your sessionID: " + this.state.mySessionId);
    const response = await https.post("api/v1/bubble-session/" + this.state.mySessionId,
      {},
      {
        headers: { "Content-Type": "application/json" },
      }
    );
    console.log("post: "+response.data.token);

    return await response.data.token; // The token
  }

  async putSession() {
    console.log("this is your sessionID: " + this.state.mySessionId);
    const response = await https.put("api/v1/bubble-session/" + this.state.mySessionId,
      {},
      {
        headers: { "Content-Type": "application/json" },
      }
    );
    console.log("put: "+response.data.token);

    return await response.data.token; // The token
  }

  // async getToken() {
  //   const sessionId = await this.createSession(this.state.mySessionId);
  //   console.log("this is your sessionID: " + sessionId);
  //   return await this.createToken(sessionId);
  // }

  // async getSessionId(sessionId) {
  //   const response = await https.post("api/v1/bubble-session/" + sessionId,
  //   {},
  //     {
  //       headers: { "Content-Type": "application/json" },
  //     }
  //   );
  //   console.log("this is your createSession sessionId: " + response.data.token);
  //   return response.data.token; // The sessionId
  // }

  // async createToken(sessionId) {
  //   const response = await axios.post(
  //     APPLICATION_SERVER_URL + "apiv2/sessions/" + sessionId + "/connections",
  //     {},
  //     {
  //       headers: { "Content-Type": "application/json" },
  //     }
  //   );
  //   console.log("this is your createToken: " + response.data);
  //   console.log(response.data);

  //   return response.data; // The token
  // }
}

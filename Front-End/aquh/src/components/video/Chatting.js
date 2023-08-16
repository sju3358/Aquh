import { OpenVidu } from "openvidu-browser";

import { formatDateTime } from "./DateUtils";
import { FaRegCalendarCheck } from "react-icons/fa";
import { FaRegCalendarTimes } from "react-icons/fa";
import { FaSignInAlt } from "react-icons/fa";

import https from "../../utils/https";
import React, { Component, useState, useEffect } from "react";
import classes from "./Chatting.module.css";
import UserVideoComponent from "./UserVideoComponent";
import ChattingSection from "./chat/ChattingSection";
import Button from "../ui/Button";
import Nav from "../ui/Nav";
import { ImExit } from "react-icons/im";

import CameraToggle from "./tool/CameraToggle";
import AudioToggle from "./tool/AudioToggle";

const APPLICATION_SERVER_URL =
  process.env.NODE_ENV === "production" ? "" : "https://i9b108.p.ssafy.io/";

export default class Chatting extends Component {
  constructor(props) {
    super(props);
    console.log(this.props);

    // These properties are in the state's component in order to re-render the HTML whenever their values change
    this.state = {
      mySessionId: this.props.mySessionId,
      myUserName: this.props.userNickname,
      session: undefined,
      mainStreamManager: undefined, // Main video of the page. Will be the 'publisher' or one of the 'subscribers'
      publisher: undefined,
      subscribers: [],
      cameraState: true,
      audioState: true,
    };

    this.createSession = this.createSession.bind(this);
    this.joinSession = this.joinSession.bind(this);
    this.leaveSession = this.leaveSession.bind(this);
    this.switchCamera = this.switchCamera.bind(this);
    this.handleMainVideoStream = this.handleMainVideoStream.bind(this);
    this.onbeforeunload = this.onbeforeunload.bind(this);

    this.cameraToggle = this.cameraToggle.bind(this);
    this.audioToggle = this.audioToggle.bind(this);
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
              var videoDevices = devices.filter((device) => device.kind === "videoinput");
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

    this.putSession().then((token) => {
      if (token !== "") {
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
                var videoDevices = devices.filter((device) => device.kind === "videoinput");
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
          }
        );
      }
    });
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

    // eslint-disable-next-line no-restricted-globals
    location.href = "/bubble";
  }

  async switchCamera() {
    try {
      const devices = await this.OV.getDevices();
      var videoDevices = devices.filter((device) => device.kind === "videoinput");

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

  // { (memberNumber === hostMemberNumber) ?
  //       (<Button onClick={this.createSession}> 채팅방 생성하기 </Button>) :
  //       (<Button onClick={this.joinSession  }> 채팅방 입장하기 </Button>)
  // }

  bubbleDetail() {
    const hostNumber = this.props.hostNumber;
    const memberNumber = this.props.memberNumber;
    const bubbleThumbnail = this.props.bubbleThumbnail;
    const bubbleContent = this.props.bubbleContent;
    const bubbleTitle = this.props.bubbleTitle;
    const bubbleType = this.props.bubbleType;
    const planOpenDate = this.props.planOpenDate;
    const planCloseDate = this.props.planCloseDate;

    let buttonActive = false;

    if (bubbleType === true) buttonActive = true;
    else {
      if (Date.parse(planCloseDate) <= Date.now()) buttonActive = true;
      else buttonActive = false;
    }

    return (
      <div>
        <Nav />
        <div className={classes.chatroomContainer}>
          <img src={bubbleThumbnail} alt="" className={classes.thumbnailImg}></img>
          <div className={classes.titleContainer}>
            <p className={classes.chatingTitle}> {bubbleTitle}</p>
            <p className={classes.bubbleType}>여기 타입 {bubbleType}</p>
          </div>
          <p className={classes.openDate}>
            {" "}
            생성 일자 :&nbsp;&nbsp;
            <FaRegCalendarCheck /> {formatDateTime(planOpenDate)}
          </p>
          <p className={classes.closeDate}>
            {" "}
            종료 일자 :&nbsp;&nbsp;
            <FaRegCalendarTimes /> {formatDateTime(planCloseDate)}
          </p>
          <p className={classes.chattingContent}>내용</p>
          <div className={classes.chattingContentBox}>{bubbleContent} </div>

          <div className={classes.entranceButtonContainer}>
            {memberNumber === hostNumber ? (
              <button
                className={classes.entranceButton}
                onClick={buttonActive ? this.createSession : null}
              >
                <FaSignInAlt />
                &nbsp; 채팅방 생성{" "}
              </button>
            ) : (
              <button
                className={classes.entranceButton}
                onClick={buttonActive ? this.joinSession : null}
              >
                <FaSignInAlt />
                &nbsp; 채팅방 입장{" "}
              </button>
            )}
          </div>
        </div>
      </div>
    );
  }

  // 카메라 온오프 토글
  cameraToggle() {
    if (this.state.cameraState) {
      this.setState({ cameraState: false });
      this.state.publisher.publishVideo(false);
    } else {
      this.setState({ cameraState: true });
      this.state.publisher.publishVideo(true);
    }
  }

  // 마이크 온오프 토글
  audioToggle() {
    if (this.state.audioState) {
      this.setState({ audioState: false });
      this.state.publisher.publishAudio(false);
    } else {
      this.setState({ audioState: true });
      this.state.publisher.publishAudio(true);
    }
  }

  bubbleChatting() {
    const memberNickname = this.state.myUserName;
    const bubbleTitle = this.props.bubbleTitle;

    return (
      <div className={classes.container}>
        <h1 className={classes.sessionTitle}>{bubbleTitle}</h1>
        <div className={classes.videoPage}>
          <div className={classes.sessionMain}>
            {this.state.publisher !== undefined ? (
              <div className={classes.streamContainer}>
                <UserVideoComponent streamManager={this.state.publisher} />
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
          <div className={classes.sessionRight}>
            <div className={classes.sessionNav}>
              <button className={classes.switchBtn} onClick={this.cameraToggle}>
                <CameraToggle cameraState={this.state.cameraState} />
              </button>

              <button className={classes.switchBtn} onClick={this.audioToggle}>
                <AudioToggle audioState={this.state.audioState} />
              </button>

              <button className={classes.controlBtn} onClick={this.leaveSession}>
                <ImExit className={classes.icon} />
              </button>
            </div>
            <div className={classes.sessionChat}>
              <ChattingSection bubbleNum={this.state.mySessionId} />
            </div>
          </div>
        </div>
      </div>
    );
  }

  render() {
    return (
      <div>{this.state.session === undefined ? this.bubbleDetail() : this.bubbleChatting()}</div>
    );
  }

  async postSession() {
    console.log("this is your sessionID: " + this.state.mySessionId);
    const response = await https.post(
      "api/v1/bubble-session/" + this.state.mySessionId,
      {},
      {
        headers: { "Content-Type": "application/json" },
      }
    );
    console.log("post: " + response.data.token);

    return await response.data.token; // The token
  }

  async putSession() {
    console.log("this is your sessionID: " + this.state.mySessionId);
    const response = await https
      .put(
        "api/v1/bubble-session/" + this.state.mySessionId,
        {},
        {
          headers: { "Content-Type": "application/json" },
        }
      )
      .catch((error) => {
        alert("아직 채팅방이 생성되지 않았습니다");
        console.log(error);
      });

    if (response != undefined) return await response.data.token; // The token
    else return "";
  }
}

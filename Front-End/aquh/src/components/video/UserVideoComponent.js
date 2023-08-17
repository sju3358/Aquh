import React, { Component } from "react";
import OpenViduVideoComponent from "./OvVideo";
import classes from "./UserVideoComponent.module.css";
export default class UserVideoComponent extends Component {

  getUserNickname(data) {
    console.log(data);
    return JSON.parse(data.split("%")[0]).clientData;
  }

  getUserLevel(data) {
    console.log(data);
    return JSON.parse(data.split("%")[0]).memberLevel;
  }

  render() {
    return (
      <div>
        {this.props.streamManager !== undefined ? (
          <div className={classes.streamcomponent}>
            <OpenViduVideoComponent streamManager={this.props.streamManager} />
            <div className={classes.videoProfile}>
              <img className={classes.videoProfileImg} src={`../../chat-profile${this.getUserLevel(this.props.streamManager.stream.connection.data)}.png`} alt="AvatarImg" />
              <p className={classes.videoProfileNickName}>
                {this.getUserNickname(this.props.streamManager.stream.connection.data)}
              </p>
            </div>
          </div>
        ) : null}
      </div>
    );
  }
}

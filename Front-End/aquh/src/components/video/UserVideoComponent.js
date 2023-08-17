import React, { Component } from "react";
import OpenViduVideoComponent from "./OvVideo";
import classes from "./UserVideoComponent.module.css";
export default class UserVideoComponent extends Component {

  getUserNickname(data) {
    console.log(data);
    return JSON.parse(data.split("%")[0]).clientData;
  }


  render() {
    return (
      <div>
        {this.props.streamManager !== undefined ? (
          <div className={classes.streamcomponent}>
            <OpenViduVideoComponent streamManager={this.props.streamManager} />
            <div className={classes.videoProfile}>
              {this.getUserNickname(this.props.streamManager.stream.connection.data)}
            </div>
          </div>
        ) : null}
      </div>
    );
  }
}

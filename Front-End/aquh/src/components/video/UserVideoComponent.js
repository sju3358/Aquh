import React, { Component } from "react";
import OpenViduVideoComponent from "./OvVideo";
import classes from "./UserVideoComponent.module.css";

export default class UserVideoComponent extends Component {
  getNicknameTag() {
    // Gets the nickName of the user
    return JSON.parse(this.props.streamManager.stream.connection.data)
      .clientData;
  }

  render() {
    return (
      <div>
        {this.props.streamManager !== undefined ? (
          // TODO : 이걸 수정해서 스타일 주면 됨!
          <div className='streamcomponent'>
            <OpenViduVideoComponent streamManager={this.props.streamManager} />
            {/* <div><p>{this.getNicknameTag()}</p></div> */}
          </div>
        ) : null}
      </div>
    );
  }
}

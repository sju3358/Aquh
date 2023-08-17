import React, { Component } from "react";
import OpenViduVideoComponent from "./OvVideo";
import classes from "./UserVideoComponent.module.css";
export default class UserVideoComponent extends Component {

  getUserNickname(data){

    return JSON.parse(data.split("%")[0]).clientData;
  }
  

  render() {
    return (
      <div>
        {this.props.streamManager !== undefined ? (
          // TODO : 이걸 수정해서 스타일 주면 됨!
          <div className="streamcomponent">
            <OpenViduVideoComponent streamManager={this.props.streamManager} />
            <div><p>{this.getUserNickname(this.props.streamManager.stream.connection.data)}</p></div>
          </div>
        ) : null}
      </div>
    );
  }
}

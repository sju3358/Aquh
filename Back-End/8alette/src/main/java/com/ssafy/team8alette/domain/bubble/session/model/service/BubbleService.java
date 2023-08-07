package com.ssafy.team8alette.domain.bubble.session.model.service;

import com.ssafy.team8alette.domain.bubble.session.model.dto.response.BubbleResponse;

public class BubbleService {

	public BubbleResponse createBubble(){

		try {
			checkUserLogged(httpSession);
		} catch (Exception e) {
			return getErrorResponse(e);
		}
		System.out.println("Getting a token from OpenVidu Server | {sessionName}=" + sessionNameParam);

		JSONObject sessionJSON = (JSONObject) new JSONParser().parse(sessionNameParam);

		// The video-call to connect
		String sessionName = (String) sessionJSON.get("sessionName");

		// Role associated to this user
		OpenViduRole role = LoginController.users.get(httpSession.getAttribute("loggedUser")).role;

		// Optional data to be passed to other users when this user connects to the
		// video-call. In this case, a JSON with the value we stored in the HttpSession
		// object on login
		String serverData = "{\"serverData\": \"" + httpSession.getAttribute("loggedUser") + "\"}";

		// Build connectionProperties object with the serverData and the role
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(role).build();

		JSONObject responseJson = new JSONObject();

		if (this.mapSessions.get(sessionName) != null) {
			// Session already exists
			System.out.println("Existing session " + sessionName);
			try {

				// Generate a new Connection with the recently created connectionProperties
				String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

				// Update our collection storing the new token
				this.mapSessionNamesTokens.get(sessionName).put(token, role);

				// Prepare the response with the token
				responseJson.put(0, token);

				// Return the response to the client
				return new ResponseEntity<>(responseJson, HttpStatus.OK);
			} catch (OpenViduJavaClientException e1) {
				// If internal error generate an error message and return it to client
				return getErrorResponse(e1);
			} catch (OpenViduHttpException e2) {
				if (404 == e2.getStatus()) {
					// Invalid sessionId (user left unexpectedly). Session object is not valid
					// anymore. Clean collections and continue as new session
					this.mapSessions.remove(sessionName);
					this.mapSessionNamesTokens.remove(sessionName);
				}
			}
		}

		// New session
		System.out.println("New session " + sessionName);
		try {

			// Create a new OpenVidu Session
			Session session = this.openVidu.createSession();
			// Generate a new Connection with the recently created connectionProperties
			String token = session.createConnection(connectionProperties).getToken();

			// Store the session and the token in our collections
			this.mapSessions.put(sessionName, session);
			this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
			this.mapSessionNamesTokens.get(sessionName).put(token, role);

			// Prepare the response with the token
			responseJson.put(0, token);

			// Return the response to the client
			return new ResponseEntity<>(responseJson, HttpStatus.OK);

		} catch (Exception e) {
			// If error generate an error message and return it to client
			return getErrorResponse(e);
		}
	}

	public void
}

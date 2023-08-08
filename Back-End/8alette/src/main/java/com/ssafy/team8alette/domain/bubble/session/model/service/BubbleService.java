package com.ssafy.team8alette.domain.bubble.session.model.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dto.request.CreateBubbleRequest;
import com.ssafy.team8alette.domain.bubble.session.model.dto.response.CreateBubbleResponse;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BubbleService {

	private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
	private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

	private final BubbleRepository bubbleRepository;
	private final OpenVidu openVidu;

	public CreateBubbleResponse createBubble(CreateBubbleRequest createBubbleRequest) throws
		OpenViduJavaClientException,
		OpenViduHttpException {

		String serverData = "{\"serverData\": \"" + createBubbleRequest.getHostMemberNumber() + "\"}";
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
			.type(ConnectionType.WEBRTC)
			.data(serverData)
			.role(OpenViduRole.PUBLISHER)
			.build();

		String sessionName = createBubbleRequest.getSessionName();

		Session session = openVidu.createSession();
		String token = session.createConnection(connectionProperties).getToken();

		this.mapSessions.put(sessionName, session);
		this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
		this.mapSessionNamesTokens.get(sessionName).put(token, role);

		// Prepare the response with the token
		responseJson.put(0, token);

		// Return the response to the client
		return new ResponseEntity<>(responseJson, HttpStatus.OK);
	}

	public void enterBubble() {
		if (this.mapSessions.get(sessionName) != null) {

			System.out.println("Existing session " + sessionName);
			try {

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
	}
}

package com.ssafy.team8alette.domain.bubble.session.model.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.ssafy.team8alette.domain.bubble.session.exception.CategoryNotFoundException;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleListRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dao.BubbleSessionRepository;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleListEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.BubbleSessionEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.entity.CategoryEntity;
import com.ssafy.team8alette.domain.bubble.session.model.dto.request.CreateBubbleRequest;
import com.ssafy.team8alette.domain.bubble.tools.model.dao.CategoryRepository;
import com.ssafy.team8alette.domain.member.auth.model.dao.MemberRepository;
import com.ssafy.team8alette.domain.member.auth.model.dto.Member;

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

	private final BubbleRepository bubbleRepository;
	private final BubbleSessionRepository bubbleSessionRepository;
	private final BubbleListRepository bubbleListRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;
	private final OpenVidu openVidu;

	private String createBubbleSession(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {

		String serverData = "{\"serverData\": \"" + sessionId + "\"}";
		ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
			.type(ConnectionType.WEBRTC)
			.data(serverData)
			.role(OpenViduRole.PUBLISHER)
			.build();

		Session session = openVidu.createSession();
		String token = session.createConnection(connectionProperties).getToken();

		Map<String, OpenViduRole> bubblers = new ConcurrentHashMap<>();
		bubblers.put(token, OpenViduRole.PUBLISHER);

		BubbleSessionEntity bubbleSession = new BubbleSessionEntity();

		bubbleSession.setSessionId(sessionId);
		bubbleSession.setSession(session);
		bubbleSession.setBubblers(bubblers);

		bubbleSessionRepository.save(bubbleSession);

		return token;
	}

	private String closeBubbleSession() {

	}

	public String createBubble(CreateBubbleRequest createBubbleRequest) throws
		OpenViduJavaClientException,
		OpenViduHttpException {

		Long hostMemberNumber = createBubbleRequest.getHostMemberNumber();
		Long categoryNumber = createBubbleRequest.getCategoryNumber();

		Member member = memberRepository.findMemberByMemberNumber(hostMemberNumber);
		CategoryEntity category = categoryRepository.findCategoryEntityByCategoryNumber(categoryNumber)
			.orElseThrow(() -> new CategoryNotFoundException());
		
		BubbleEntity bubble = BubbleEntity.builder()
			.bubbleTitle(createBubbleRequest.getBubbleTitle())
			.bubbleContent((createBubbleRequest.getBubbleContent()))
			.bubbleThumbnail(createBubbleRequest.getBubbleThumbnail())
			.bubbleType(createBubbleRequest.isBubbleType())
			.bubbleState(true)
			.categoryEntity(category)
			.hostMember(member)
			.build();

		bubbleRepository.save(bubble);

		String sessionId = Long.toString(bubble.getBubbleNumber());
		String bubbleToken = createBubbleSession(sessionId);

		return bubbleToken;
	}

	private void openBubble() {

	}

	private void closeBubble() {

	}

	public void enterBubble() {

		if (map)

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

	//이건 버블 리스트 서비스로 빼기
	private void createBubbleList(Member member, BubbleEntity bubble) {
		BubbleListEntity bubbleListEntity = BubbleListEntity.builder()
			.bubbleEntity(bubble)
			.member(member)
			.build();

		bubbleListRepository.save(bubbleListEntity);
	}

}

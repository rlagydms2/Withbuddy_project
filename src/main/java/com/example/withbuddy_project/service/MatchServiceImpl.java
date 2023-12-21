package com.example.withbuddy_project.service;

import com.example.withbuddy_project.domain.Match;
import com.example.withbuddy_project.domain.User;
import com.example.withbuddy_project.domain.dto.MatchRequest;
import com.example.withbuddy_project.domain.dto.MatchResponse;
import com.example.withbuddy_project.repository.MatchRepository;
import com.example.withbuddy_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    private MatchRepository matchRepository;
    private UserRepository userRepository;
//    private SimpMessagingTemplate template;


    @Autowired
    public MatchServiceImpl(SqlSession sqlSession) {
      matchRepository = sqlSession.getMapper(MatchRepository.class);
      userRepository = sqlSession.getMapper(UserRepository.class);
    }

    public int save(Long senderId,Long receiverId) { // 수락 버튼을 누르면 매칭 저장
        Match match=Match.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .accept(false)
                .build();
        Match sender=Match.builder()
                .senderId(receiverId)
                .receiverId(senderId)
                .accept(false)
                .build();
        matchRepository.save(sender);
        return matchRepository.save(match);
    }


    public boolean ifExist(Long senderId,Long receiverId) { // 매칭 존재여부 확인
        Match match = matchRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        return (match != null) ? true : false; //존재하면 true 아니면 false, false인 경우에 save되게
    }


    public int acceptMatch(MatchRequest match) {
        return matchRepository.update(match);  // 매칭을 수락하면 accept를 true로 업데이트
    }


    public List<MatchResponse> list(Long userId) { // 로그인한 유저의 매칭요청 리스트
        List<MatchResponse> matchResponses = matchRepository.findByLoginUserId(userId);    //userId인 matchinglist 불러오기
        List<MatchResponse> responses = new ArrayList<>();
        for (int i = 0; i < matchResponses.size(); i++) {
            User user = userRepository.findById(matchResponses.get(i).getSenderId());
            MatchResponse matchResponse=MatchResponse
                    .builder()
                    .buddyImage(matchResponses.get(i).getBuddyImage())
                    .senderId(matchResponses.get(i).getSenderId())
                    .userId(user.getUserId())
                    .build();
            responses.add(matchResponse);
        }
        log.info("responses: {} ", responses);
        return responses;
    }

    public void delete(MatchRequest request) {  // 삭제
        Match match = matchRepository.findBySenderIdAndReceiverId(request.getReceiverId(), request.getSenderId());
        matchRepository.delete(match.getId());
    }
}

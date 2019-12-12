package edu.springz.security.domain;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import edu.springz.domain.MemberVO;
import lombok.Getter;

@Getter
public class CustomUser extends User {
   
   private MemberVO mvo;

   public CustomUser(MemberVO mvo) {
         super(mvo.getUserid(), mvo.getUserpw(), mvo.getAuthList().stream().
                        map(auth -> new SimpleGrantedAuthority(auth.getAuth())).collect(Collectors.toList()));
         // User 클래스를 상속받기 때문에
         // MemberVO를 파라미터로 받아서 생성자 호출 ( User 클래스에 맞게 생성자 호출해야함 )
         // AuthVO 인스턴스는 GrantedAuthority 객체로 변환해야하기 때문에 stream()과 map()을 이용함
         this.mvo = mvo;
      }
   
   public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
      super(username, password, authorities);
   }
}
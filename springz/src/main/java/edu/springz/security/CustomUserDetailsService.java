package edu.springz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.springz.domain.MemberVO;
import edu.springz.mapper.MemberMapper;
import edu.springz.security.domain.CustomUser;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class CustomUserDetailsService implements UserDetailsService {
   // 원래 DB에서 ID, PW, AUTH 가져올 때는 정해진 컬럼명만 사용가능했는데
   // 내가 원하는 컬럼명을 이용하고싶으면 UserDetailsService를 구현해서 만들면 된다.
   
   @Setter(onMethod_ = @Autowired)
   private MemberMapper memberMapper;
   
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      log.warn("CustomUserDetailsService.loadUserByUsername() : " + username);
      MemberVO mvo = memberMapper.read(username);
      // 파라미터로 받은 username을 통해 Mapper-Mybatis-DB에서 유저 정보 하나 읽어오는데
      
      return mvo==null ? null : new CustomUser(mvo);
      // mvo값이 null이면 null을 리턴하고, 아니면 UserDetails를 return 해준다.
      // 반환값이 UserDetails : CustomUser (User를 구현한 class)를 생성하면 그 값이 UserDetails
   }

}
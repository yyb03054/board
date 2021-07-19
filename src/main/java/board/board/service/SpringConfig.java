package board.board.service;


import board.board.domain.Post;
import board.board.repository.*;
import board.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    DataSource datasource;

    @Autowired
    public SpringConfig(DataSource datasource) {
        this.datasource = datasource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){ return new JdbcMemberRepository(datasource);  }

    @Bean
    public PostService postService() { return new PostService(postRepository()); }

    @Bean
    public PostRepository postRepository() { return new JdbcPostRepository(datasource); }
}

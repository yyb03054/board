package board.board.service;


import board.board.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    DataSource datasource;

    private EntityManager em;
    private final MemberRepository memberRepository;


    public SpringConfig(DataSource datasource, EntityManager em, MemberRepository memberRepository) {
        this.datasource = datasource;
        this.em = em;
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

    /*@Bean
    public MemberRepository memberRepository(){
     //   return new JdbcTemplateMemberRepository(datasource);
        //return new JpaMemberRepository(em);
    }*/

    @Bean
    public PostService postService() { return new PostService(postRepository()); }

    @Bean
    public PostRepository postRepository() { return new JdbcPostRepository(datasource); }
}

package board.board.repository;

import board.board.domain.Post;
import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcPostRepository implements PostRepository {
    private final DataSource dataSource;

    public JdbcPostRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Post save(Post post) {
        String sql = "insert into post(name, content) values(?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, post.getName());
            pstmt.setString(2, post.getContent());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                post.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return post;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "select * from post where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();
            if(rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setName(rs.getString("name"));
                post.setContent(rs.getString("content"));
                return Optional.of(post);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Post> findAll() {
        String sql = "select * from post";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Post> posts = new ArrayList<>();
            while(rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setName(rs.getString("name"));
                post.setContent(rs.getString("content"));
                posts.add(post);
            }
            return posts;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Post> findByName(String name) {
        String sql = "select * from post where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Post post = new Post();
                post.setId(rs.getLong("id"));
                post.setName(rs.getString("name"));
                post.setContent(rs.getString("content"));
                return Optional.of(post);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Post> findByContent(String content) {
        String sql = "select * from post where content = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, content);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Post post1 = new Post();
                post1.setId(rs.getLong("id"));
                post1.setName(rs.getString("name"));
                post1.setContent(rs.getString("content"));
                return Optional.of(post1);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
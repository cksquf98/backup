package com.carstay.demo.src.review;



import com.carstay.demo.src.review.model.GetDetailReviewRes;
import com.carstay.demo.src.review.model.GetReviewRes;
import com.carstay.demo.src.review.model.PostReviewReq;
import com.carstay.demo.src.user.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetReviewRes> getReviews() {
        String getUsersQuery = "select ReviewNum, Title, Content, ReviewImage, ReviewGrade, WriterId, ReviewTime, Spot from Review";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetReviewRes(
                        rs.getInt("ReviewNum"),
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getString("ReviewImage"),
                        rs.getString("ReviewGrade"),
                        rs.getString("WriterId"),
                        rs.getString("ReviewTime"),
                        rs.getString("Spot"))
        );
    }

    public List<GetReviewRes> getReviewsBySpot(int spotId) {
        String getUsersQuery = "select ReviewNum, Title, Content, ReviewImage, ReviewGrade, WriterId, ReviewTime, Spot from Review where spot = ?";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetReviewRes(
                        rs.getInt("ReviewNum"),
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getString("ReviewImage"),
                        rs.getString("ReviewGrade"),
                        rs.getString("WriterId"),
                        rs.getString("ReviewTime"),
                        rs.getString("Spot")),
                spotId);
    }
    public List<GetDetailReviewRes> getDetailReview(int reviewNum) {
        String getUsersQuery = "select Review.ReviewNum, Title, Content, ReviewImage, ReviewGrade,WriterId, ReviewTime, Spot, CommentContent, CommentWriter, CommentTime from Review outer join Comment on Review.ReviewNum= Comment.ReviewNum where Review.ReviewNum= ?";

        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetDetailReviewRes(
                        rs.getInt("ReviewNum"),
                        rs.getString("Title"),
                        rs.getString("Content"),
                        rs.getString("ReviewImage"),
                        rs.getString("ReviewGrade"),
                        rs.getString("WriterId"),
                        rs.getString("ReviewTime"),
                        rs.getString("Spot"),
                        rs.getString("CommentContent"),
                        rs.getString("CommentWriter"),
                        rs.getString("CommentTime")),reviewNum);
    }

    public int createReview(String userId, PostReviewReq postReviewReq){
        System.out.println(postReviewReq.getTitle());
        String createReviewQuery = "insert into Review (Title, Content, ReviewImage, ReviewGrade, WriterId, Spot) values (?, ?, ?, ?, ?, ?)";
        Object[] createQueryParams = new Object[]{postReviewReq.getTitle(), postReviewReq.getContent(), postReviewReq.getReviewImage(), postReviewReq.getReviewGrade(), userId, postReviewReq.getSpot()};
        this.jdbcTemplate.update(createReviewQuery, createQueryParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int createReview2(PostReviewReq postReviewReq) {

        System.out.println(postReviewReq.getTitle());
        String createReviewQuery = "insert into Review (Title, Content, ReviewImage, ReviewGrade, WriterId, Spot) values (?, ?, ?, ?, ?, ?)";
        Object[] createQueryParams = new Object[]{postReviewReq.getTitle(), postReviewReq.getContent(), postReviewReq.getReviewImage(), postReviewReq.getReviewGrade(),postReviewReq.getWriterId(), postReviewReq.getSpot()};
        this.jdbcTemplate.update(createReviewQuery, createQueryParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }
}




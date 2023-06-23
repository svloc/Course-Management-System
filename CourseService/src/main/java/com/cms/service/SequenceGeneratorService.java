// package com.cms.service;

// import org.springframework.beans.factory.annotation.Autowired;
//  import org.springframework.stereotype.Service;

// import com.cms.model.Course;
// import com.cms.repository.CourseRepository;

// @Service
// public class SequenceGeneratorService {
    // @Autowired
    // private CourseRepository courseRepository;

    // public String generateCourseId() {
    //     long courseCount = courseRepository.count();
    //     if (courseCount == 0) {
    //         return "200";
    //     }
    //     Course highestCourse = courseRepository.findTopByOrderByCourseIdDesc();
    //     int highestCourseId = Integer.parseInt(highestCourse.getCourseId());
    //     int newCourseId = highestCourseId + 1;

    //     return String.valueOf(newCourseId);
    // }
// }


package com.cms.service;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cms.model.DatabaseSequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;



@Service
public class SequenceGeneratorService {

    /*declaring Mongo Operations a object named mongo*/
	
    @Autowired
    public MongoOperations mongo;

    public int getSequenceNum(String seqName) {
        Query query=new Query(Criteria.where("id").is(seqName));
        Update update = new Update().inc("seq", 1);
        DatabaseSequence counter= mongo.findAndModify(query, update, options().returnNew(true).upsert(true), DatabaseSequence.class);
        return (int) (!Objects.isNull(counter) ? counter.getSeq() : 1);
    }
}
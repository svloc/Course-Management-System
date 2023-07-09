package com.cms.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.cms.model.Admission;
public interface AdmissionRepository extends MongoRepository<Admission, Long> {

    List<Admission> findCoursesByAssociateId(String associateId);

    List<Admission> findByCourseId(String courseId);

    void deleteByCourseId(String courseId);
}

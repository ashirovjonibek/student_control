package uz.controlstudentserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.controlstudentserver.dto.*;
import uz.controlstudentserver.entity.Direction;
import uz.controlstudentserver.entity.Education;
import uz.controlstudentserver.entity.Faculty;
import uz.controlstudentserver.repository.SubjectRepository;
import uz.controlstudentserver.repository.UserRepository;
import uz.controlstudentserver.servise.*;

import java.util.UUID;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
//@PreAuthorize("hasAnyRole({'ROLE_DEAN','ROLE_DEPUTY_DEAN'})")
@RequestMapping("api/education")
public class EducationController {

    @Autowired
    EducationService educationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FacultyService facultyService;

    @Autowired
    DirectionService directionService;

    @Autowired
    GroupService groupService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    SubjectRepository subjectRepository;


    //for education entity
    @PostMapping("/saveOrEditEducation")
    public HttpEntity<?> saveOrEditEducation(@RequestBody EduDto dto){
        return ResponseEntity.ok(educationService.saveOrEdit(dto));
    }

    @GetMapping("/getAllEducation")
    public HttpEntity<?> getAllEducation(){
        return ResponseEntity.ok(educationService.getAll());
    }

    @GetMapping("/getEducationById/{id}")
    public HttpEntity<?> getEducationById(@PathVariable Integer id){
        return ResponseEntity.ok(educationService.getOne(id));
    }


    //for faculty entity
    @PostMapping("/saveOrEditFaculty")
    public HttpEntity<?> saveOrEditFaculty(@RequestBody FacultyDto dto){
        return ResponseEntity.ok(facultyService.saveOrEdit(dto));
    }

    @GetMapping("/getFacultyById/{id}")
    public HttpEntity<?> getFacultyById(@PathVariable Integer id){
        return ResponseEntity.ok(facultyService.getOne(id));
    }

    @GetMapping("/getFacultyByEduId/{id}")
    public HttpEntity<?> getFacultyByEduId(@PathVariable Integer id){
        return ResponseEntity.ok(facultyService.getByEdu(((Education) educationService.getOne(id).getObject())));
    }

    //for direction entity
    @PostMapping("/saveOrEditDirection")
    public HttpEntity<?> saveOrEditDirection(@RequestBody DirectionDto directionDto){
        return ResponseEntity.ok(directionService.saveOrEdit(directionDto));
    }

    @GetMapping("/getByIdDirection/{id}")
    public HttpEntity<?> getByIdDirection(@PathVariable Integer id){
        return ResponseEntity.ok(id);
    }

    @GetMapping("/getDirectionByFacultyId/{id}")
    public HttpEntity<?> getDirectionByFacultyId(@PathVariable Integer id){
        return ResponseEntity.ok(directionService.getByFaculty(((Faculty) facultyService.getOne(id).getObject())));
    }

    //for group entity
    @PostMapping("/saveOrEditGroup")
    public HttpEntity<?> saveOrEditGroup(@RequestBody GroupDto dto){
        return ResponseEntity.ok(groupService.saveOrEdit(dto));
    }

    @GetMapping("/getGroupById/{lid}")
    public HttpEntity<?> getGroupById(@PathVariable Integer id){
        return ResponseEntity.ok(groupService.getOne(id));
    }

    @GetMapping("/getGroupByDirectionId/{id}")
    public HttpEntity<?> getGroupByDirectionId(@PathVariable Integer id){
        return ResponseEntity.ok(groupService.findByDirection(((Direction) directionService.getOne(id).getObject())));
    }

    @GetMapping("/getGroupByTeacherId/{id}")
    public HttpEntity<?> getGroupByTeacherId(@PathVariable UUID id){
        return ResponseEntity.ok(groupService.findByTeacher(userRepository.findById(id).orElseThrow(()->new IllegalStateException("user nor found for group teacher"))));
    }

    @GetMapping("/getAllSubjectByTeacherId/{id}")
    public HttpEntity<?> getAllSubjectByTeacherId(@PathVariable UUID id){
        return ResponseEntity.ok(
                subjectService.findAllByTeacherId(id)
        );
    }

    @PostMapping("/saveOrEditSubject")
    public HttpEntity<?> saveOrEditSubject(@RequestBody SubjectDto dto){
        return ResponseEntity.ok(subjectService.saveOrEdit(dto));
    }

    @GetMapping("existsByName/{name}")
    public HttpEntity<?> existsByName(@PathVariable String name){
        return ResponseEntity.ok(subjectRepository.existsByName(name));
    }

}

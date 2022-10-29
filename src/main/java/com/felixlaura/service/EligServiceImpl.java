package com.felixlaura.service;

import com.felixlaura.binding.EligResponse;
import com.felixlaura.entity.*;
import com.felixlaura.repository.*;
import com.zaxxer.hikari.util.ConcurrentBag;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class EligServiceImpl implements EligService {

    @Autowired
    private DcCaseRepo dcCaseRepo;

    @Autowired
    private PlanRepository planRepo;

    @Autowired
    private DcIncomeRepo incomeRepo;

    @Autowired
    private DcChildrenRepo childrenRepo;

    @Autowired
    private CitizenAppRepository appRepo;

    @Autowired
    private DcEducationRepo educationRepo;

    @Autowired
    private EligDtlsRepository eligDtlsRepository;

    @Autowired
    private CoTriggerRepository coTrgRepo;

    @Override
    public EligResponse determineEligibility(Long caseNum) {

        Optional<DcCaseEntity> caseEntity = dcCaseRepo.findById(caseNum);
        Integer planId = null;
        String planName = null;
        Integer appId = null;

        if (caseEntity.isPresent()) {
            DcCaseEntity dcCaseEntity = caseEntity.get();
            planId = dcCaseEntity.getPlanId();
            appId = dcCaseEntity.getAppId();
        }

        Optional<PlanEntity> planEntity = planRepo.findById(planId);
        if (planEntity.isPresent()) {
            PlanEntity plan = planEntity.get();
            planName = plan.getPlanName();
        }

        Optional<CitizenAppEntity> app = appRepo.findById(appId);
        Integer age = 0;
        CitizenAppEntity citizenAppEntity = null;
        if(app.isPresent()){
            citizenAppEntity = app.get();
            LocalDate dob = citizenAppEntity.getDob();
            LocalDate now = LocalDate.now();
            age = Period.between(dob, now).getYears();
        }

        EligResponse eligResponse = executePlanConditions(caseNum, planName, age);

        //logic to store data in db
        EligDtlsEntity eligDtlsEntity = new EligDtlsEntity();
        BeanUtils.copyProperties(eligResponse, eligDtlsEntity);

        eligDtlsEntity.setCaseNum(caseNum);
        eligDtlsEntity.setHolderName(citizenAppEntity.getFullName());
        eligDtlsEntity.setHolderSsn(citizenAppEntity.getSsn());

        eligDtlsRepository.save(eligDtlsEntity);


        CoTriggerEntity coEntity = new CoTriggerEntity();
        coEntity.setCaseNum(caseNum);
        coEntity.setTrgStatus("Pending");

        coTrgRepo.save(coEntity);

        return eligResponse;
    }

    private EligResponse executePlanConditions(Long caseNum, String planName, Integer age) {

        EligResponse response = new EligResponse();
        response.setPlanName(planName);

        DcIncomeEntity income = incomeRepo.findByCaseNum(caseNum);

        if ("SNAP".equals(planName)) {
            Double empIncome = income.getEmpIncome();
            if (empIncome <= 300) {
                response.setPlanStatus("AP");
            } else {
                response.setPlanStatus("DN");
                response.setDenialReason("High Income");
            }
        } else if ("CCAP".equals(planName)) {
            boolean ageCondition = true;
            boolean kidsCountCondition = false;
            List<DcChildrenEntity> childs = childrenRepo.findByCaseNum(caseNum);
            if (!childs.isEmpty()) {
                kidsCountCondition = true;
                for (DcChildrenEntity entity : childs) {
                    Integer childAge = entity.getChildAge();
                    if (childAge > 16) {
                        ageCondition = false;
                        break;
                    }
                }

            }

            if (income.getEmpIncome() <= 300 && kidsCountCondition && ageCondition) {
                response.setPlanStatus("AP");
            } else {
                response.setPlanStatus("DN");
                response.setDenialReason("Not satisfied business rules");
            }


        } else if ("Medicaid".equals(planName)) {

            Double empIncome = income.getEmpIncome();
            Double propertyIncome = income.getPropertyIncome();

            if(empIncome <= 300 && propertyIncome == 0){
                response.setPlanStatus("AP");
            } else {
                response.setPlanStatus("DN");
                response.setDenialReason("High Income");
            }

        } else if ("Medicare".equals(planName)) {

                if(age >= 65){
                    response.setPlanStatus("AP");
                } else {
                    response.setPlanStatus("DN");
                    response.setDenialReason("Age not Matched");
                }

        } else if ("NJW".equals(planName)) {
            DcEducationEntity educationEntity = educationRepo.findByCaseNum(caseNum);
            Integer graduationYear = educationEntity.getGraduationYear();

            int currentYear = LocalDate.now().getYear();

            if(income.getEmpIncome() <= 0 && graduationYear < currentYear){
                response.setPlanStatus("AP");
            } else {
                response.setPlanStatus("DN");
                response.setDenialReason("Rules Not Satisfied");
            }
        }


        if(response.getPlanStatus().equals("AP")){
            response.setPlanStartDate(LocalDate.now());
            response.setPlanEndDate(LocalDate.now().plusMonths(6));
            response.setBenefitAmt(350.00);
        }

        return response;
    }
}

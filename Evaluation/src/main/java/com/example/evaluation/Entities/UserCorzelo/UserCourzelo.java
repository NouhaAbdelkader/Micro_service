package com.example.evaluation.Entities.UserCorzelo;

import com.example.evaluation.Entities.UserCorzelo.Role;
import com.example.evaluation.Entities.UserCorzelo.Speciality;
import com.example.evaluation.Entities.UserCorzelo.badgeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserCourzelo {
        private  String id;
        private String firstName;
        private String username;
        private String lastName;
        private String email;
        private String password;
        private String sexe;
        private Date date_of_birth;
        private Date date_of_creation;
        private int nbMaxHeurePerWeek;
        private int nbHourPerWeek=0;
        private Set<Role> roles = new HashSet<>();
        private String companyName;
        private String descriptionRecruiter;
        private float scoreXp;
        private List<badgeType> badges;
        private String resume;
        private Speciality speciality;
        private int level =1 ;
        private boolean approved=false;
        private int validVoteCount=0;
        private boolean canVote=true;
        private int nbVoteForIncentives=0;
        private int nbPrimeVoteForBadges;
        private int PaymentDay;
        private String CentreOffIntrest;
        private Float overAll_average;
        private Module module;
        public UserCourzelo(String username, String email, String password) {
                this.username = username;
                this.email = email;
                this.password = password;




        }
        public UserCourzelo(String id) {
                this.id = id;
                // Initialize other fields if needed
        }


        @Override
        public String toString() {
                return "UserCourzelo{" +
                        "id='" + id + '\'' +
                        ", firstName='" + firstName + '\'' +
                        ", username='" + username + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", email='" + email + '\'' +
                        ", password='" + password + '\'' +
                        ", sexe='" + sexe + '\'' +
                        ", date_of_birth=" + date_of_birth +
                        ", date_of_creation=" + date_of_creation +
                        ", nbMaxHeurePerWeek=" + nbMaxHeurePerWeek +
                        ", nbHourPerWeek=" + nbHourPerWeek +
                        ", roles=" + roles +
                        ", companyName='" + companyName + '\'' +
                        ", descriptionRecruiter='" + descriptionRecruiter + '\'' +
                        ", scoreXp=" + scoreXp +
                        ", badges=" + badges +
                        ", resume='" + resume + '\'' +
                        ", speciality=" + speciality +
                        ", level=" + level +
                        ", approved=" + approved +
                        ", validVoteCount=" + validVoteCount +
                        ", canVote=" + canVote +
                        ", nbVoteForIncentives=" + nbVoteForIncentives +
                        ", nbPrimeVoteForBadges=" + nbPrimeVoteForBadges +
                        ", PaymentDay=" + PaymentDay +
                        ", CentreOffIntrest='" + CentreOffIntrest + '\'' +
                        ", overAll_average=" + overAll_average +
                        '}';
        }
}




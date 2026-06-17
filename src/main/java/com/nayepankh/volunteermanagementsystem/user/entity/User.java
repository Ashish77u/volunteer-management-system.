package com.nayepankh.volunteermanagementsystem.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_username", columnList = "username")
})
@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @Column(name = "create_at", updatable = true)
    private LocalDate createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;


    // EAGER because every security check needs roles immediately
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();



}


/*

    Agar interviewer puche:

@ManyToMany relation kaise maintain hota hai?

Answer:

In a Many-to-Many relationship, Hibernate creates or uses a junction table (user_roles). The junction table contains foreign keys from both parent tables. The relationship is maintained through user_roles.user_id referencing users.id and user_roles.role_id referencing roles.id.


*******  Why use Enum for Roles?

Enum provides type safety, prevents invalid role values, improves readability, and avoids hard-coded strings throughout the application.


******* Kya Inverse Side Mandatory Hai?
❌ Nahi

Inverse side optional hoti hai.

Tumhara current design:

User -----> Role

Matlab:

User se Role access kar sakte ho
Role se User access nahi kar sakte

Ye completely valid hai.

Isko Unidirectional ManyToMany bolte hain

Current code:

User
  |
  | @ManyToMany
  ↓
Role

Sirf ek direction me navigation possible hai.

Example:

User user = userRepository.findById(1L);

user.getRoles();

✅ Kaam karega

Lekin:

Role role = roleRepository.findById(1L);

role.getUsers();

❌ Nahi chalega

Kyuki Role entity me:

private Set<User> users;

hai hi nahi.

***************** Ye database design ke bahut important concepts hain jo interviews me frequently pooche jaate hain:

Normalization
Denormalization
Standardization (ye database theory ka formal concept nahi hai, lekin industry me standards follow karne ke context me use hota hai)
1️⃣ Normalization
Definition

Normalization ek process hai jisme database ko aise design karte hain ki:

Data duplication (redundancy) kam ho
Data consistency bani rahe
Update, insert, delete anomalies na aaye
Example (Without Normalization)
Student Table
student_id	student_name	course_name	teacher_name
1	Ashish	Java	Rahul
2	Amit	Java	Rahul
3	Neha	Java	Rahul

Problem:

Rahul 3 baar repeat ho raha hai
Java 3 baar repeat ho raha hai
After Normalization
Students
student_id	student_name
1	Ashish
2	Amit
3	Neha
Courses
course_id	course_name
101	Java
Teachers
teacher_id	teacher_name
1	Rahul
Student_Course
student_id	course_id
1	101
2	101
3	101

Benefits:

✅ Less duplication

✅ Better consistency

✅ Easy maintenance

Normal Forms
1NF (First Normal Form)

Rule:

No repeating groups
Each cell contains a single value

❌ Bad

id	phones
1	123,456

✅ Good

id	phone
1	123
1	456
2NF

Rule:

Must be in 1NF
No partial dependency
3NF

Rule:

Must be in 2NF
No transitive dependency

Interview me mostly:

1NF
2NF
3NF

tak hi poocha jata hai.

2️⃣ Denormalization
Definition

Denormalization means:

Performance improve karne ke liye intentionally duplicate data rakhna.

Example

Normalized Design:

orders
customers
products

Data dekhne ke liye:

SELECT *
FROM orders
JOIN customers
JOIN products;

Multiple joins lagte hain.

Denormalized:

orders
order_id	customer_name
1	Ashish

Customer name directly orders table me store.

Benefits:

✅ Faster reads

✅ Fewer joins

Drawbacks:

❌ Data duplication

❌ More storage

❌ Update issues

Real-world Example

E-commerce:

Amazon
Flipkart

Read-heavy systems me denormalization use hota hai.

Normalization vs Denormalization
Feature	Normalization	Denormalization
Redundancy	Low	High
Consistency	High	Medium
Storage	Less	More
Joins	More	Less
Read Performance	Slower	Faster
Write Performance	Better consistency	Complex
3️⃣ Standardization
Important

Database theory me:

Normalization ≠ Standardization

Industry me Standardization ka matlab hota hai:

Naming Standards

✅ Good

customer_id
first_name
created_at

❌ Bad

custid
fn
cdate
Data Type Standards
id BIGINT
email VARCHAR(100)
created_at TIMESTAMP
Coding Standards

Example:

UserService
UserController
UserRepository

Consistent naming.

SQL Standards
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE
);

Consistent structure.

Real Hospital Management Example
Normalized
patients
doctors
appointments
bills

Separate tables.

Denormalized

appointments table:

doctor_name
patient_name

directly stored.

Faster read but duplicated data.

Interview Answer
What is Normalization?

Normalization is the process of organizing data into multiple related tables to reduce redundancy and improve data integrity.

What is Denormalization?

Denormalization is the process of intentionally adding redundant data to improve read performance and reduce joins.

What is Standardization?

Standardization means following consistent naming conventions, data types, schema design, and coding standards across the database and application.

One-Line Memory Trick
Normalization   → Reduce redundancy
Denormalization → Improve performance
Standardization → Maintain consistency and standards




 */
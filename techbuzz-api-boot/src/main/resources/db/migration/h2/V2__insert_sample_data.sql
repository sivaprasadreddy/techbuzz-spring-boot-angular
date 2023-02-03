INSERT INTO users (email, password, name, role, verified, created_at)
VALUES ('admin@sivalabs.com', '$2a$10$RpvziVEv9nbYQgbGS7QiD.if5/54eT1MgaxJjsADn2TLGJUYuQbv2', 'Admin', 'ROLE_ADMIN', true,
        CURRENT_TIMESTAMP),
       ('sivalabs.in@gmail.com', '$2a$10$RpvziVEv9nbYQgbGS7QiD.if5/54eT1MgaxJjsADn2TLGJUYuQbv2', 'SivaLabs', 'ROLE_ADMIN', true,
        CURRENT_TIMESTAMP)
;


INSERT INTO categories(name, slug, description, image, display_order)
VALUES ('Java', 'java', 'Java and JVM related technology news, blogs, discussions etc', 'java.png', 1),
       ('WebDev', 'webdev', 'Web Development using HTML, JS, CSS and its rich ecosystem of frameworks and libraries', 'webdev.png', 2),
       ('Go', 'go', 'The Go Programming Language', 'go.png', 3),
       ('Python', 'python', 'Python Programming Language', 'python.png', 3),
       ('Cloud', 'cloud', 'Cloud Computing', 'cloud.png', 5),
       ('DevOps', 'devops', 'DevOps culture, practices, techniques', 'devops.png', 6),
       ('Testing', 'testing', 'Software Testing Techniques and technologies', 'testing.png', 7),
       ('Career', 'career', 'Career Guidance and Support', 'career.png', 8),
       ('General', 'general', 'Anything related to Software Development', 'general.png', 9)
;

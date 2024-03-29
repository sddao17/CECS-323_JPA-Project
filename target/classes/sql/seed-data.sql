INSERT INTO PUBLISHERS (NAME, EMAIL, PHONE) VALUES ('Penguin Random House', 'prandomhouse@penguin.com', '1-562-473-1238')
INSERT INTO PUBLISHERS (NAME, EMAIL, PHONE) VALUES ('HarperCollins', 'hcpgroup@gmail.com', '1-830-773-1024')
INSERT INTO PUBLISHERS (NAME, EMAIL, PHONE) VALUES ('No Publish Rubbish', 'nopubrub@yahoo.com', '1-920-234-8812')
INSERT INTO AUTHORING_ENTITIES (EMAIL, AUTHORING_ENTITY_TYPE, NAME, HEAD_WRITER, YEAR_FORMED) VALUES ('frankherbert@gmail.com', 'IndividualAuthors', 'Frank Herbert', null, null)
INSERT INTO AUTHORING_ENTITIES (EMAIL, AUTHORING_ENTITY_TYPE, NAME, HEAD_WRITER, YEAR_FORMED) VALUES ('georgerr@martin.com', 'IndividualAuthors', 'George R. R. Martin', null, null)
INSERT INTO AUTHORING_ENTITIES (EMAIL, AUTHORING_ENTITY_TYPE, NAME, HEAD_WRITER, YEAR_FORMED) VALUES ('pat@rothfuss.com', 'IndividualAuthors', 'Patrick Rothfuss', null, null)
INSERT INTO AUTHORING_ENTITIES (EMAIL, AUTHORING_ENTITY_TYPE, NAME, HEAD_WRITER, YEAR_FORMED) VALUES ('onthewrittenfly@gmail.com', 'AdHocTeams', 'On The Written Fly', null, null)
INSERT INTO AUTHORING_ENTITIES (EMAIL, AUTHORING_ENTITY_TYPE, NAME, HEAD_WRITER, YEAR_FORMED) VALUES ('bakeandcreate@gmail.com', 'AdHocTeams', 'Bake And Create', null, null)
INSERT INTO AUTHORING_ENTITIES (EMAIL, AUTHORING_ENTITY_TYPE, NAME, HEAD_WRITER, YEAR_FORMED) VALUES ('thumbfiddlers@gmail.com', 'WritingGroups', 'Thumb Fiddlers', 'Susan C. Anthony',  1987)
INSERT INTO AUTHORING_ENTITIES (EMAIL, AUTHORING_ENTITY_TYPE, NAME, HEAD_WRITER, YEAR_FORMED) VALUES ('welovescience@ufosarereal.com', 'WritingGroups', 'Scientists United', 'Bob H. Hewly', 1980)
INSERT INTO AD_HOC_TEAMS_MEMBER (INDIVIDUAL_AUTHORS_EMAIL, AD_HOC_TEAMS_EMAIL) VALUES ('frankherbert@gmail.com', 'onthewrittenfly@gmail.com')
INSERT INTO AD_HOC_TEAMS_MEMBER (INDIVIDUAL_AUTHORS_EMAIL, AD_HOC_TEAMS_EMAIL) VALUES ('georgerr@martin.com', 'bakeandcreate@gmail.com')
INSERT INTO BOOKS (ISBN, TITLE, YEAR_PUBLISHED, AUTHORING_ENTITIES_EMAIL, PUBLISHER_NAME) VALUES ('874213982', 'Dune', 1965, 'frankherbert@gmail.com', 'Penguin Random House')
INSERT INTO BOOKS (ISBN, TITLE, YEAR_PUBLISHED, AUTHORING_ENTITIES_EMAIL, PUBLISHER_NAME) VALUES ('135792468', 'Primes and Prejudice', 2002, 'welovescience@ufosarereal.com', 'HarperCollins')
INSERT INTO BOOKS (ISBN, TITLE, YEAR_PUBLISHED, AUTHORING_ENTITIES_EMAIL, PUBLISHER_NAME) VALUES ('214231534', 'The Name of The Wind', 2013, 'pat@rothfuss.com', 'No Publish Rubbish')
CREATE TABLE QuizupMatchTable(
	id INTEGER not NULL, 
	
	matchId VARCHAR(16),
	firstId VARCHAR(16),
	secondId VARCHAR(16),
	topicId VARCHAR(16), 
	
	matchResult INTEGER,
	firstLog VARCHAR(4000),
	secondLog VARCHAR(4000),
	
	createdDate DATE,
	
	PRIMARY KEY ( id ), 
	UNIQUE INDEX MATCH_INDEX (MatchId),
	INDEX FIRST_INDEX (firstId),
	INDEX SECOND_INDEX (secondId),
	INDEX TOPIC_INDEX (topicId),
	INDEX TIME_INDEX USING BTREE (createdDate)
);
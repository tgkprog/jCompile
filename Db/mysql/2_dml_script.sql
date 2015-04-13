USE `JCompileDb`;

delete from CodeQuestion where true;
INSERT INTO `CodeQuestion` 
( `question_code`, `question_Summary`, `question`, `rating`, 
`tester_class_name`, `test_properties`, `validator_class_name`) VALUES
 ( `q1`, 
 `Program to add two numbers`, 
 `Class to add two ints. Return int. <br>Function signature : int add(int i, int j)`, `1`, 
 `s2n.putCorrect.here`, `a=1`
 , `s2n.jComp.services.utl.StrictSrcCodeValidator` ),

 ( `q2`, 
 `Class to multiply two numbers`, 
 `Class  to multiply two ints. Return int. <br>Function signature : int mul(int a, int b)`, `1`, 
 `s2n.putCorrect.here`, `a=1`
  , `s2n.jComp.services.utl.StrictSrcCodeValidator`)
 
 ;
 
 CREATE USER 'u1'@'localhost' IDENTIFIED BY 'p1';
 GRANT ALL PRIVILEGES ON *.* TO 'u1'@'localhost' IDENTIFIED BY 'p1' WITH GRANT OPTION MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
 GRANT ALL PRIVILEGES ON `JCompileDb`.* TO 'u1'@'localhost'; 
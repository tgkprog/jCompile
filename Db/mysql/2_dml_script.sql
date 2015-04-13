USE `JCompileDb`;

delete from CodeQuestion where true;
INSERT INTO `CodeQuestion` 
( `question_code`, `question_Summary`, `question`, `rating`, 
`tester_class_name`, `test_properties`) VALUES
 ( 'q1', 
 'Program to add two numbers', 
 'Class to add two ints. Return int. <br>Function signature : int add(int i, int j)', '1', 
 's2n.putCorrect.here', 'a=1'),

 ( 'q2', 
 'Class to multiply two numbers', 
 'Class  to multiply two ints. Return int. <br>Function signature : int mul(int a, int b)', '1', 
 's2n.putCorrect.here', 'a=1')
 
 ;
%%%%%%%%%% Product Discount System %%%%%%%%%%%%
Author: M.Sreehari
Date:16/Dec/2017
Description:This tool calculates discount for the selected apparels

Prerequisites:
%%%%%%%%%%%%%%%%%%%%%%
Java:  jdk1.8.0_144
Maven: 3.x

Git location:
%%%%%%%%%%%%%

https://github.com/msreeharigit/proddiscountsys

Steps to build project:
%%%%%%%%%%%%%
1)Unzip project downloaded from Git on system
2)Go to %PROJECT_PATH% (ex: C:\>CD ProductDiscountSystem)
3)C:\>ProductDiscountSystem>mvn -U  clean  install
4)The above command should make the build and executes TestNG testcases.
5)Open C:\>ProductDiscountSystem\target\surefire-reports\index.html to see the test case report. 



Sample Input to be entered on console
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

5
1,Arrow,Shirts,800
2,Vero Moda,Dresses,1400
3,Provogue,Footwear,1800
4,Wrangler,Jeans,2200
5,UCB,Shirts,1500

2
1,2,3,4
1,5

Note: To make user friendly, I added readable prompts while taking input from console
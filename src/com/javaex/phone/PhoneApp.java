package com.javaex.phone;

import java.util.List;
import java.util.Scanner;

public class PhoneApp {
	public static void main(String[] args) {
		PhoneDao phoneDao = new PhoneDao();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("*************************************");
		System.out.println("*         전화번호 관리 프로그램         *");
		System.out.println("*************************************");
		
		int n;	//메뉴번호
		do {
			System.out.println("\n1.리스트 2.등록 3.수정 4.삭제 5.검색 6.종료");
			System.out.println("-------------------------------------");
			System.out.print(">메뉴번호: ");
			n = sc.nextInt();
			switch(n) {
				case 1:
					System.out.println("<1.리스트>");
					List<PersonVo> personList = phoneDao.personSelect();
					for(PersonVo personVo:personList) {
						personVo.showList();
					}
					break;
				case 2:
					System.out.println("<2.등록>");
					PersonVo iVo = new PersonVo();
					System.out.print("이름 > ");
					iVo.setName(sc.next());
					System.out.print("휴대전화 > ");
					iVo.setHp(sc.next());
					System.out.print("회사번호 > ");
					iVo.setCompany(sc.next());
					phoneDao.personInsert(iVo);
					break;
				case 3:
					System.out.println("<3.수정>");
					PersonVo uVo = new PersonVo();
					System.out.print("번호 > ");
					uVo.setPersonId(sc.nextInt());
					System.out.print("이름 > ");
					uVo.setName(sc.next());
					System.out.print("휴대전화 > ");
					uVo.setHp(sc.next());
					System.out.print("회사번호 > ");
					uVo.setCompany(sc.next());
					phoneDao.personUpdate(uVo);
					break;
				case 4:
					System.out.println("<4.삭제>");
					PersonVo dVo = new PersonVo();
					System.out.print("번호 > ");
					dVo.setPersonId(sc.nextInt());
					phoneDao.personDelete(dVo);
					break;
				case 5:
					System.out.println("<5.검색>");
					System.out.println("검색어 > ");
					List<PersonVo> personSearch = phoneDao.personSearch();
					for(PersonVo personVo:personSearch) {
						personVo.showList();
					}
					break;
				case 6:
					System.out.println("*************************************");
					System.out.println("*              감사합니다             *");
					System.out.println("*************************************");
					break;
				default:
					System.out.println("[다시 입력해주세요.]");
			}
		} while(n!=6); 
		sc.close();
	}
}

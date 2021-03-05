package board.dto;

import lombok.Data;

// 롬복 라이브러리를 사용하여 기본적으로 필요한 getter/setter를 자동으로 생성함
@Data

public class BoardDto {
//	private은 외부에서 접근 불가능함.
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String createdId;
	private String createdDatetime;
	private String updaterId;
	private String updatedDatetime;
	private String deletedYn;
	
//	원래는 private으로 설정된 것을 외부에서 사용하기 위해서 아래와 같이 getter setter 선언해서 (해당 이름 긁어서 오른쪽마우스 - refactor - encapsulation 눌러서 만들거나, 위의 source - generate getter/setter 해서 생성)
//	사용해야 하지만, 롬복을 다운 받아서, 위에처럼 @Data 만 적고, 오류표시 눌러서 import 해주면, 아래와 같이 일일이 다 getter/setter 안 넣어줘도 private 으로 설정된 것들을 외부에서 접근 가능하게 사용할 수 있다.
	
//	public int getBoardIdx() {
//		return boardIdx;
//	}
//	
//	public void setBoardIdx(int boardIdx) {
//		this.boardIdx = boardIdx;
//	}

}

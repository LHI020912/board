package entity;

import java.time.LocalDate;

public interface BoardList {
	Integer getBoardId();
	String getTitle();
	String getLoginId();
	LocalDate getCreateDate();
	int getViewCount();
}

package Homee;

import java.time.LocalDateTime;

public class CustomerSupport {
	private int tokenNumber;
	private String query;
	private LocalDateTime reportDate;
	private LocalDateTime closeDate;
	private boolean queryStatus;

	public int getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(int tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public LocalDateTime getReportDate() {
		return reportDate;
	}

	public void setReportDate(LocalDateTime reportDate) {
		this.reportDate = reportDate;
	}

	public LocalDateTime getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(LocalDateTime closeDate) {
		this.closeDate = closeDate;
	}

	public boolean isQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(boolean queryStatus) {
		this.queryStatus = queryStatus;
	}
}

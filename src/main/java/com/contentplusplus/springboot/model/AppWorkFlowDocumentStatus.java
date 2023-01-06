package com.contentplusplus.springboot.model;

import java.util.Optional;

public enum AppWorkFlowDocumentStatus {
	DRAFT, REVIEW_REJECTED, REVIEW, APPROVE_REJECTED, APPROVED, FINALIZE_REJECTED, FINALIZED, PUBLISHED;

	private AppWorkFlowDocumentStatus prevStatus = null;
	private AppWorkFlowDocumentStatus nextStatus = null;

	static {
		AppWorkFlowDocumentStatus[] values = AppWorkFlowDocumentStatus.values();
		for (int i = 1; i < values.length; i++) {
			values[i].prevStatus = values[i - 1];
		}
		for (int i = 0; i < values.length - 1; i++) {
			values[i].nextStatus = values[i + 1];
		}
	}

	public Optional<AppWorkFlowDocumentStatus> prev() {
		return Optional.ofNullable(prevStatus);
	}

	public Optional<AppWorkFlowDocumentStatus> next() {
		return Optional.ofNullable(nextStatus);
	}

	public static void main(String[] args) {
		System.out.println(AppWorkFlowDocumentStatus.PUBLISHED.next());
		if (AppWorkFlowDocumentStatus.PUBLISHED.next().isEmpty()) {
			System.out.println(AppWorkFlowDocumentStatus.PUBLISHED + " is the last Status.");
		}
		System.out.println(AppWorkFlowDocumentStatus.DRAFT.prev());
	}
}
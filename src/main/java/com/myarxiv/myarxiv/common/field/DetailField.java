package com.myarxiv.myarxiv.common.field;

public enum DetailField {
    paper_journal_reference("Journal reference"),paper_acm_class("ACM classification"),paper_msc_class("MSC classification"),
    paper_report_number("Report number"),paper_doi("DOI");

    private String field;

    DetailField(String field) {
        this.field = field;
    }
    public String getField(){
        return this.field;
    }


}

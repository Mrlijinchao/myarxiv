package com.myarxiv.myarxiv.common.field;

public enum Fields {

    all("All Fields"),paper_title("Title"), paper_authors("Author"),paper_abstract("Abstract"),paper_comments("Comments"),
    paper_journal_reference("Journal reference"),paper_acm_class("ACM classification"),paper_msc_class("MSC classification"),
    paper_report_number("Report number"),paper_identifier("arXiv identifier"),paper_doi("DOI"),user_id("arXiv author ID");

    private String field;

    Fields(String field) {
        this.field = field;
    }
    public String getField(){
        return this.field;
    }

}

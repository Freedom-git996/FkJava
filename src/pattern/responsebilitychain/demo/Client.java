package pattern.responsebilitychain.demo;

public class Client {

    public static void main(String[] args) {
        PurchaseRequest purchaseRequest = new PurchaseRequest(1, 150000, 1);

        DepartmentApprover departmentApprover = new DepartmentApprover("张主任");
        CollegeApprover collegeApprover = new CollegeApprover("李院长");
        ViceSchoolMasterApprover viceSchoolMasterApprover = new ViceSchoolMasterApprover("吴副院");
        SchoolMasterApprover schoolMasterApprover = new SchoolMasterApprover("周院长");

        // 形成环状责任链
        departmentApprover.setApprover(collegeApprover);
        collegeApprover.setApprover(viceSchoolMasterApprover);
        viceSchoolMasterApprover.setApprover(schoolMasterApprover);
        schoolMasterApprover.setApprover(departmentApprover);

        departmentApprover.processRequest(purchaseRequest);
    }
}

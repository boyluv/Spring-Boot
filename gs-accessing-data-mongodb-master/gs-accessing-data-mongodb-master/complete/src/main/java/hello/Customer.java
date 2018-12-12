package hello;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;


public class Customer {

    @Id
    public String id;

    public String firstName;
    public String lastName;
    public DetailPhone detailPhone;

    public Customer() {}
    UnwindOperation unwindOperation = Aggregation.unwind("reviews");
    Aggregation aggregation = Aggregation.newAggregation(unwindOperation);
    AggregationResults<UnwindedHotel> results=mongoOperations.aggregate(aggregation,"hotel", UnwindedHotel.class);


    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.detailPhone = new DetailPhone("","");
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
    public class DetailPhone {
        private String color;
        private String os;

        public DetailPhone(){}
        public DetailPhone(String color, String os) {
            this.color = color;
            this.os = os;
        }

    }

}


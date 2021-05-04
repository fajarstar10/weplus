package id.weplus.model.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import id.weplus.model.response.insureduser.InsuredUser;

public class DataTertanggungRequest implements Parcelable {
    private String fullname;
    private String email;
    private String dob;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("payment_channel")
    private String paymentChannel;
    private String phone;
    @SerializedName("identification_no")
    private String idNo;
    private String address;
    private String province;
    private String city;
    private String sex;
    @SerializedName("zip_code")
    private String zipCode;
    @SerializedName("beneficiary_name")
    private String beneficiaryName;
    @SerializedName("beneficiary_id")
    private String beneficiaryIdNo;
    @SerializedName("beneficiary_email")
    private String beneficiaryEmail;
    @SerializedName("beneficiary_dob")
    private String beneficiaryDob;
    @SerializedName("beneficiary_sex")
    private String beneficiarySex;
    @SerializedName("beneficiary_relation")
    private String beneficiaryRelation;
    @SerializedName("beneficiary_address")
    private String beneficiaryAddress;
    @SerializedName("beneficiary_phone")
    private String beneficiaryPhone;
    @SerializedName("beneficiary_province")
    private String beneficiaryProvince;
    @SerializedName("beneficiary_city")
    private String beneficiaryCity;
    @SerializedName("datetime_start")
    private String dateTimeStart;
    @SerializedName("polis_holder_fullname")
    private String polisHolderFullName;
    @SerializedName("polis_holder_sex")
    private String polisHolderSex;
    @SerializedName("polis_holder_dob")
    private String polisHolderDob;
    @SerializedName("polis_holder_pob")
    private String polisHolderPob;
    @SerializedName("polis_holder_identification_no")
    private String polisHolderIdNo;
    @SerializedName("polis_holder_relation")
    private String polisHolderRelation;
    @SerializedName("polis_holder_phone")
    private String polisHolderPhone;
    @SerializedName("polis_holder_email")
    private String polisHolderEmail;
    @SerializedName("polis_holder_address")
    private String polisHolderAddress;
    @SerializedName("polis_holder_city")
    private String polisHolderCity;
    @SerializedName("motor_frame")
    private String motorFrame;
    @SerializedName("plat_no")
    private String platNo;
    @SerializedName("stnk_name")
    private String stnkName;
    @SerializedName("machine_no")
    private String engineNo;
    @SerializedName("color")
    private String motorColor;
    @SerializedName("is_new")
    private String condition;
    @SerializedName("agree_tnc")
    private int agreeTnc;
    @SerializedName("height")
    private int height;
    @SerializedName("weight")
    private int weight;
    @SerializedName("motorcycle_id")
    private int motorId;
    @SerializedName("addition_driver_incident")
    private int additionDriverIncident;
    @SerializedName("addition_passenger_incident")
    private int additionPassegerIncident;
    @SerializedName("addition_protection")
    private String additionProtection;
    @SerializedName("type_insurance")
    private int typeInsurance;
    @SerializedName("plat_id")
    private int platId;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("is_automatic")
    private String isAutomatic;
    @SerializedName("addition_tpl")
    private String additionTpl;
    @SerializedName("car_price")
    private String carPrice;
    @SerializedName("car_id")
    private String carId;
    @SerializedName("accessories_price")
    private String accPrice;
    @SerializedName("accessories")
    private String acc;
    @SerializedName("car_frame")
    private String carFrame;
    @SerializedName("job")
    private String job;
    @SerializedName("pob")
    private String pob;
    @SerializedName("district")
    private String district;
    @SerializedName("sub_district")
    private String subDistrict;
    @SerializedName("polis_holder_province")
    private String polisHolderProvince;
    @SerializedName("departure_date")
    private String departureDate;
    @SerializedName("departure_city")
    private String departureCity;
    @SerializedName("return_date")
    private String returnDate;
    @SerializedName("package_type")
    private String packageType;
    @SerializedName("destination")
    private String destination;
    @SerializedName("duration")
    private String duration;
    @SerializedName("addition_insured")
    private String additionInsured;
    @SerializedName("job_declaration")
    private String jobDeclaration;
    @SerializedName("adult")
    private String adult;
    @SerializedName("child")
    private String child;
    @SerializedName("group_type")
    private String groupType;
    @SerializedName("is_smoking")
    private String isSmoking;
    @SerializedName("addition_data")
    private String additionData;
    @SerializedName("polis_holder_zip_code")
    private String polisHolderZipCode;
    @SerializedName("car_brand")
    private String carBrand;
    @SerializedName("car_series")
    private String carSeries;
    @SerializedName("insured_address")
    private String insuredAddress;
    @SerializedName("insured_city")
    private String insuredCity;
    @SerializedName("insured_province")
    private String insuredProvince;
    @SerializedName("occupation")
    private String occupation;
    @SerializedName("keuskupan")
    private String keuskupan;
    @SerializedName("paroki")
    private String paroki;
    @SerializedName("lingkungan")
    private String lingkungan;
    @SerializedName("family_role")
    private String familyRole;
    @SerializedName("family_role_other")
    private String family_role_other;
    @SerializedName("other_job")
    private String jobOther;
    @SerializedName("protection_type")
    private String protectionType;
    @SerializedName("address_ktp")
    private String addressKtp;
    @SerializedName("payment_type")
    private String paymentType;
    @SerializedName("relation")
    private String relation;
    @SerializedName("health_index")
    private int categoryIndex;
    @SerializedName("type_group")
    private String typeGroup;
    @SerializedName("type")
    private String type;
    @SerializedName("departure")
    private String departure;
    @SerializedName("komunitas")
    private String komunitas;
    private String gadget_type;
    private String gadget_name;
    private String gadget_brand;
    private String gadget_age;
    private String gadget_price;
    private String serial_number;
    private String imei;
    @SerializedName("is_agent")
    private String isAgent;
    @SerializedName("trip_reason")
    private String tripReason;
    @SerializedName("code_booking")
    private String codeBooking;
    @SerializedName("detail_flight")
    private String detailFlight;
    @SerializedName("depart_flight_number")
    private String departFlightNumber;
    @SerializedName("return_flight_number")
    private String returnFlightNumber;
    @SerializedName("return_code_booking")
    private String returnCodeBooking;
    @SerializedName("return_destination")
    private String returnDestination;
    @SerializedName("return_departure_date")
    private String returnDepartureDate;
    @SerializedName("arrival_date")
    private String arrivalDate;
    @SerializedName("ticket_price")
    private String ticketPrice;
    @SerializedName("return_ticket_price")
    private String returnTicketPrice;
    @SerializedName("return_departure")
    private String returnDeparture;
    @SerializedName("return_arrival_date")
    private String returnArrivalDate;
    @SerializedName("partner_weplus_id")
    private String partnerWePlusId;
    @SerializedName("nik")
    private String nik;
    @SerializedName("number_of_passenger")
    private int carPassenger;

    public int getCarPassenger() {
        return carPassenger;
    }

    public void setCarPassenger(int carPassenger) {
        this.carPassenger = carPassenger;
    }

    public String getPartnerWePlusId() {

        return partnerWePlusId;
    }

    public void setPartnerWePlusId(String partnerWePlusId) {
        this.partnerWePlusId = partnerWePlusId;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getReturnArrivalDate() {
        return returnArrivalDate;
    }

    public void setReturnArrivalDate(String returnArrivalDate) {
        this.returnArrivalDate = returnArrivalDate;
    }

    public String getReturnDeparture() {
        return returnDeparture;
    }

    public void setReturnDeparture(String returnDeparture) {
        this.returnDeparture = returnDeparture;
    }

    public String getReturnTicketPrice() {
        return returnTicketPrice;
    }

    public void setReturnTicketPrice(String returnTicketPrice) {
        this.returnTicketPrice = returnTicketPrice;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public static Creator<DataTertanggungRequest> getCREATOR() {
        return CREATOR;
    }

    public void setDetailFlight(String detailFlight) {
        this.detailFlight = detailFlight;
    }

    public void setDepartFlightNumber(String departFlightNumber) {
        this.departFlightNumber = departFlightNumber;
    }

    public void setReturnFlightNumber(String returnFlightNumber) {
        this.returnFlightNumber = returnFlightNumber;
    }

    public void setReturnCodeBooking(String returnCodeBooking) {
        this.returnCodeBooking = returnCodeBooking;
    }

    public void setReturnDestination(String returnDestination) {
        this.returnDestination = returnDestination;
    }

    public void setReturnDepartureDate(String returnDepartureDate) {
        this.returnDepartureDate = returnDepartureDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartFlightNumber() {
        return departFlightNumber;
    }

    public String getReturnFlightNumber() {
        return returnFlightNumber;
    }

    public String getReturnCodeBooking() {
        return returnCodeBooking;
    }

    public String getReturnDestination() {
        return returnDestination;
    }

    public String getReturnDepartureDate() {
        return returnDepartureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getDetailFlight() {
        return detailFlight;
    }

    public String getCodeBooking() {
        return codeBooking;
    }

    public void setCodeBooking(String codeBooking) {
        this.codeBooking = codeBooking;
    }

    public String getTripReason() {
        return tripReason;
    }

    public void setTripReason(String tripReason) {
        this.tripReason = tripReason;
    }

    public String getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(String isAgent) {
        this.isAgent = isAgent;
    }

    public String getGadget_type() {
        return gadget_type;
    }

    public void setGadget_type(String gadget_type) {
        this.gadget_type = gadget_type;
    }

    public String getGadget_name() {
        return gadget_name;
    }

    public void setGadget_name(String gadget_name) {
        this.gadget_name = gadget_name;
    }

    public String getGadget_brand() {
        return gadget_brand;
    }

    public void setGadget_brand(String gadget_brand) {
        this.gadget_brand = gadget_brand;
    }

    public String getGadget_age() {
        return gadget_age;
    }

    public void setGadget_age(String gadget_age) {
        this.gadget_age = gadget_age;
    }

    public String getGadget_price() {
        return gadget_price;
    }

    public void setGadget_price(String gadget_price) {
        this.gadget_price = gadget_price;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getKomunitas() {
        return komunitas;
    }

    public void setKomunitas(String komunitas) {
        this.komunitas = komunitas;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeGroup() {
        return typeGroup;
    }

    public void setTypeGroup(String typeGroup) {
        this.typeGroup = typeGroup;
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }

    public void setCategoryIndex(int categoryIndex) {
        this.categoryIndex = categoryIndex;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAddressKtp() {
        return addressKtp;
    }

    public void setAddressKtp(String addressKtp) {
        this.addressKtp = addressKtp;
    }

    public String getProtectionType() {
        return protectionType;
    }

    public void setProtectionType(String protectionType) {
        this.protectionType = protectionType;
    }

    public String getKeuskupan() {
        return keuskupan;
    }

    public void setKeuskupan(String keuskupan) {
        this.keuskupan = keuskupan;
    }

    public String getParoki() {
        return paroki;
    }

    public void setParoki(String paroki) {
        this.paroki = paroki;
    }

    public String getLingkungan() {
        return lingkungan;
    }

    public void setLingkungan(String lingkungan) {
        this.lingkungan = lingkungan;
    }

    public String getFamilyRole() {
        return familyRole;
    }

    public void setFamilyRole(String familyRole) {
        this.familyRole = familyRole;
    }

    public String getFamily_role_other() {
        return family_role_other;
    }

    public void setFamily_role_other(String family_role_other) {
        this.family_role_other = family_role_other;
    }

    public String getJobOther() {
        return jobOther;
    }

    public void setJobOther(String jobOther) {
        this.jobOther = jobOther;
    }

    public String getInsuredAddress() {
        return insuredAddress;
    }

    public void setInsuredAddress(String insuredAddress) {
        this.insuredAddress = insuredAddress;
    }

    public String getInsuredCity() {
        return insuredCity;
    }

    public void setInsuredCity(String insuredCity) {
        this.insuredCity = insuredCity;
    }

    public String getInsuredProvince() {
        return insuredProvince;
    }

    public void setInsuredProvince(String insuredProvince) {
        this.insuredProvince = insuredProvince;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }


    public String getPolisHolderZipCode() {
        return polisHolderZipCode;
    }

    public void setPolisHolderZipCode(String polisHolderZipCode) {
        this.polisHolderZipCode = polisHolderZipCode;
    }

    public String getJobDeclaration() {
        return jobDeclaration;
    }

    public void setJobDeclaration(String jobDeclaration) {
        this.jobDeclaration = jobDeclaration;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getIsSmoking() {
        return isSmoking;
    }

    public void setIsSmoking(String isSmoking) {
        this.isSmoking = isSmoking;
    }

    public String getAdditionData() {
        return additionData;
    }

    public void setAdditionData(String additionData) {
        this.additionData = additionData;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAdditionInsured() {
        return additionInsured;
    }

    public void setAdditionInsured(String additionInsured) {
        this.additionInsured = additionInsured;
    }

    public String getPolisHolderProvince() {
        return polisHolderProvince;
    }

    public void setPolisHolderProvince(String polisHolderProvince) {
        this.polisHolderProvince = polisHolderProvince;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getBeneficiaryIdentificationNo() {
        return beneficiaryIdentificationNo;
    }

    public void setBeneficiaryIdentificationNo(String beneficiaryIdentificationNo) {
        this.beneficiaryIdentificationNo = beneficiaryIdentificationNo;
    }

    @SerializedName("beneficiary_identification_no")
    private String beneficiaryIdentificationNo;

    public String getCarFrame() {
        return carFrame;
    }

    public void setCarFrame(String carFrame) {
        this.carFrame = carFrame;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(String carPrice) {
        this.carPrice = carPrice;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getAccPrice() {
        return accPrice;
    }

    public void setAccPrice(String accPrice) {
        this.accPrice = accPrice;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getAdditionTpl() {
        return additionTpl;
    }

    public void setAdditionTpl(String additionTpl) {
        this.additionTpl = additionTpl;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getPlatId() {
        return platId;
    }

    public void setPlatId(int platId) {
        this.platId = platId;
    }

    public int getTypeInsurance() {
        return typeInsurance;
    }

    public void setTypeInsurance(int typeInsurance) {
        this.typeInsurance = typeInsurance;
    }

    public int getAdditionDriverIncident() {
        return additionDriverIncident;
    }

    public void setAdditionDriverIncident(int additionDriverIncident) {
        this.additionDriverIncident = additionDriverIncident;
    }

    public int getAdditionPassegerIncident() {
        return additionPassegerIncident;
    }

    public void setAdditionPassegerIncident(int additionPassegerIncident) {
        this.additionPassegerIncident = additionPassegerIncident;
    }

    public String getAdditionProtection() {
        return additionProtection;
    }

    public void setAdditionProtection(String additionProtection) {
        this.additionProtection = additionProtection;
    }

    public int getMotorId() {
        return motorId;
    }

    public void setMotorId(int motorId) {
        this.motorId = motorId;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getAgreeTnc() {
        return agreeTnc;
    }

    public void setAgreeTnc(int agreeTnc) {
        this.agreeTnc = agreeTnc;
    }

    public String getMotorFrame() {
        return motorFrame;
    }

    public void setMotorFrame(String motorFrame) {
        this.motorFrame = motorFrame;
    }

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public String getStnkName() {
        return stnkName;
    }

    public void setStnkName(String stnkName) {
        this.stnkName = stnkName;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getMotorColor() {
        return motorColor;
    }

    public void setMotorColor(String motorColor) {
        this.motorColor = motorColor;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIsAutomatic() {
        return isAutomatic;
    }

    public void setIsAutomatic(String isAutomatic) {
        this.isAutomatic = isAutomatic;
    }

    public String getPolisHolderFullName() {
        return polisHolderFullName;
    }

    public void setPolisHolderFullName(String polisHolderFullName) {
        this.polisHolderFullName = polisHolderFullName;
    }

    public String getPolisHolderSex() {
        return polisHolderSex;
    }

    public void setPolisHolderSex(String polisHolderSex) {
        this.polisHolderSex = polisHolderSex;
    }

    public String getPolisHolderDob() {
        return polisHolderDob;
    }

    public void setPolisHolderDob(String polisHolderDob) {
        this.polisHolderDob = polisHolderDob;
    }

    public String getPolisHolderPob() {
        return polisHolderPob;
    }

    public void setPolisHolderPob(String polisHolderPob) {
        this.polisHolderPob = polisHolderPob;
    }

    public String getPolisHolderIdNo() {
        return polisHolderIdNo;
    }

    public void setPolisHolderIdNo(String polisHolderIdNo) {
        this.polisHolderIdNo = polisHolderIdNo;
    }

    public String getPolisHolderRelation() {
        return polisHolderRelation;
    }

    public void setPolisHolderRelation(String polisHolderRelation) {
        this.polisHolderRelation = polisHolderRelation;
    }

    public String getPolisHolderPhone() {
        return polisHolderPhone;
    }

    public void setPolisHolderPhone(String polisHolderPhone) {
        this.polisHolderPhone = polisHolderPhone;
    }

    public String getPolisHolderEmail() {
        return polisHolderEmail;
    }

    public void setPolisHolderEmail(String polisHolderEmail) {
        this.polisHolderEmail = polisHolderEmail;
    }

    public String getPolisHolderAddress() {
        return polisHolderAddress;
    }

    public void setPolisHolderAddress(String polisHolderAddress) {
        this.polisHolderAddress = polisHolderAddress;
    }

    public String getPolisHolderCity() {
        return polisHolderCity;
    }

    public void setPolisHolderCity(String polisHolderCity) {
        this.polisHolderCity = polisHolderCity;
    }

    public String getBeneficiaryProvince() {
        return beneficiaryProvince;
    }

    public void setBeneficiaryProvince(String beneficiaryProvince) {
        this.beneficiaryProvince = beneficiaryProvince;
    }

    public String getBeneficiaryCity() {
        return beneficiaryCity;
    }

    public void setBeneficiaryCity(String beneficiaryCity) {
        this.beneficiaryCity = beneficiaryCity;
    }

    public String getBeneficiaryPhone() {
        return beneficiaryPhone;
    }

    public void setBeneficiaryPhone(String beneficiaryPhone) {
        this.beneficiaryPhone = beneficiaryPhone;
    }

    public String getBeneficiaryAddress() {
        return beneficiaryAddress;
    }

    public void setBeneficiaryAddress(String beneficiaryAddress) {
        this.beneficiaryAddress = beneficiaryAddress;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public DataTertanggungRequest(String fullname, String email, String dob, String phone, String idNo, String address, String province, String city, String sex) {
        this.fullname = fullname;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.idNo = idNo;
        this.address = address;
        this.province = province;
        this.city = city;
        this.sex = sex;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBeneficiaryIdNo() {
        return beneficiaryIdNo;
    }

    public void setBeneficiaryIdNo(String beneficiaryIdNo) {
        this.beneficiaryIdNo = beneficiaryIdNo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryEmail() {
        return beneficiaryEmail;
    }

    public void setBeneficiaryEmail(String beneficiaryEmail) {
        this.beneficiaryEmail = beneficiaryEmail;
    }

    public String getBeneficiaryDob() {
        return beneficiaryDob;
    }

    public void setBeneficiaryDob(String beneficiaryDob) {
        this.beneficiaryDob = beneficiaryDob;
    }

    public String getBeneficiarySex() {
        return beneficiarySex;
    }

    public void setBeneficiarySex(String beneficiarySex) {
        this.beneficiarySex = beneficiarySex;
    }

    public String getBeneficiaryRelation() {
        return beneficiaryRelation;
    }

    public void setBeneficiaryRelation(String beneficiaryRelation) {
        this.beneficiaryRelation = beneficiaryRelation;
    }

    public String getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(String dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public DataTertanggungRequest() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullname);
        dest.writeString(this.email);
        dest.writeString(this.dob);
        dest.writeString(this.productId);
        dest.writeString(this.paymentChannel);
        dest.writeString(this.phone);
        dest.writeString(this.idNo);
        dest.writeString(this.address);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.sex);
        dest.writeString(this.zipCode);
        dest.writeString(this.beneficiaryName);
        dest.writeString(this.beneficiaryIdNo);
        dest.writeString(this.beneficiaryEmail);
        dest.writeString(this.beneficiaryDob);
        dest.writeString(this.beneficiarySex);
        dest.writeString(this.beneficiaryRelation);
        dest.writeString(this.beneficiaryAddress);
        dest.writeString(this.beneficiaryPhone);
        dest.writeString(this.beneficiaryProvince);
        dest.writeString(this.beneficiaryCity);
        dest.writeString(this.dateTimeStart);
        dest.writeString(this.polisHolderFullName);
        dest.writeString(this.polisHolderSex);
        dest.writeString(this.polisHolderDob);
        dest.writeString(this.polisHolderPob);
        dest.writeString(this.polisHolderIdNo);
        dest.writeString(this.polisHolderRelation);
        dest.writeString(this.polisHolderPhone);
        dest.writeString(this.polisHolderEmail);
        dest.writeString(this.polisHolderAddress);
        dest.writeString(this.polisHolderCity);
        dest.writeString(this.motorFrame);
        dest.writeString(this.platNo);
        dest.writeString(this.stnkName);
        dest.writeString(this.engineNo);
        dest.writeString(this.motorColor);
        dest.writeString(this.condition);
        dest.writeInt(this.agreeTnc);
        dest.writeInt(this.height);
        dest.writeInt(this.weight);
        dest.writeInt(this.motorId);
        dest.writeInt(this.additionDriverIncident);
        dest.writeInt(this.additionPassegerIncident);
        dest.writeString(this.additionProtection);
        dest.writeInt(this.typeInsurance);
        dest.writeInt(this.platId);
        dest.writeString(this.startDate);
        dest.writeString(this.isAutomatic);
        dest.writeString(this.additionTpl);
        dest.writeString(this.carPrice);
        dest.writeString(this.carId);
        dest.writeString(this.accPrice);
        dest.writeString(this.acc);
        dest.writeString(this.carFrame);
        dest.writeString(this.beneficiaryIdentificationNo);
        dest.writeString(this.job);
        dest.writeString(this.pob);
        dest.writeString(this.district);
        dest.writeString(this.subDistrict);
        dest.writeString(this.polisHolderProvince);
        dest.writeString(this.departureDate);
        dest.writeString(this.departureCity);
        dest.writeString(this.returnDate);
        dest.writeString(this.packageType);
        dest.writeString(this.destination);
        dest.writeString(this.duration);
        dest.writeString(this.additionInsured);
        dest.writeString(this.jobDeclaration);
        dest.writeString(this.adult);
        dest.writeString(this.child);
        dest.writeString(this.groupType);
        dest.writeString(this.isSmoking);
        dest.writeString(this.additionData);
        dest.writeString(this.polisHolderZipCode);
        dest.writeString(this.carBrand);
        dest.writeString(this.carSeries);
        dest.writeString(this.insuredAddress);
        dest.writeString(this.insuredCity);
        dest.writeString(this.insuredProvince);
        dest.writeString(this.occupation);
        dest.writeString(this.keuskupan);
        dest.writeString(this.paroki);
        dest.writeString(this.lingkungan);
        dest.writeString(this.familyRole);
        dest.writeString(this.family_role_other);
        dest.writeString(this.jobOther);
        dest.writeString(this.protectionType);
        dest.writeString(this.addressKtp);
        dest.writeString(this.paymentType);
        dest.writeString(this.relation);
        dest.writeInt(this.categoryIndex);
        dest.writeString(this.typeGroup);
        dest.writeString(this.type);
        dest.writeString(this.departure);
        dest.writeString(this.komunitas);
        dest.writeString(this.gadget_type);
        dest.writeString(this.gadget_name);
        dest.writeString(this.gadget_brand);
        dest.writeString(this.gadget_age);
        dest.writeString(this.gadget_price);
        dest.writeString(this.serial_number);
        dest.writeString(this.imei);
        dest.writeString(this.isAgent);
        dest.writeString(this.tripReason);
        dest.writeString(this.codeBooking);
        dest.writeString(this.detailFlight);
        dest.writeString(this.departFlightNumber);
        dest.writeString(this.returnFlightNumber);
        dest.writeString(this.returnCodeBooking);
        dest.writeString(this.returnDestination);
        dest.writeString(this.returnDepartureDate);
        dest.writeString(this.arrivalDate);
        dest.writeString(this.ticketPrice);
        dest.writeString(this.returnTicketPrice);
        dest.writeString(this.returnDeparture);
        dest.writeString(this.returnArrivalDate);
        dest.writeString(this.partnerWePlusId);
        dest.writeString(this.nik);
        dest.writeInt(this.carPassenger);
    }

    protected DataTertanggungRequest(Parcel in) {
        this.fullname = in.readString();
        this.email = in.readString();
        this.dob = in.readString();
        this.productId = in.readString();
        this.paymentChannel = in.readString();
        this.phone = in.readString();
        this.idNo = in.readString();
        this.address = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.sex = in.readString();
        this.zipCode = in.readString();
        this.beneficiaryName = in.readString();
        this.beneficiaryIdNo = in.readString();
        this.beneficiaryEmail = in.readString();
        this.beneficiaryDob = in.readString();
        this.beneficiarySex = in.readString();
        this.beneficiaryRelation = in.readString();
        this.beneficiaryAddress = in.readString();
        this.beneficiaryPhone = in.readString();
        this.beneficiaryProvince = in.readString();
        this.beneficiaryCity = in.readString();
        this.dateTimeStart = in.readString();
        this.polisHolderFullName = in.readString();
        this.polisHolderSex = in.readString();
        this.polisHolderDob = in.readString();
        this.polisHolderPob = in.readString();
        this.polisHolderIdNo = in.readString();
        this.polisHolderRelation = in.readString();
        this.polisHolderPhone = in.readString();
        this.polisHolderEmail = in.readString();
        this.polisHolderAddress = in.readString();
        this.polisHolderCity = in.readString();
        this.motorFrame = in.readString();
        this.platNo = in.readString();
        this.stnkName = in.readString();
        this.engineNo = in.readString();
        this.motorColor = in.readString();
        this.condition = in.readString();
        this.agreeTnc = in.readInt();
        this.height = in.readInt();
        this.weight = in.readInt();
        this.motorId = in.readInt();
        this.additionDriverIncident = in.readInt();
        this.additionPassegerIncident = in.readInt();
        this.additionProtection = in.readString();
        this.typeInsurance = in.readInt();
        this.platId = in.readInt();
        this.startDate = in.readString();
        this.isAutomatic = in.readString();
        this.additionTpl = in.readString();
        this.carPrice = in.readString();
        this.carId = in.readString();
        this.accPrice = in.readString();
        this.acc = in.readString();
        this.carFrame = in.readString();
        this.beneficiaryIdentificationNo = in.readString();
        this.job = in.readString();
        this.pob = in.readString();
        this.district = in.readString();
        this.subDistrict = in.readString();
        this.polisHolderProvince = in.readString();
        this.departureDate = in.readString();
        this.departureCity = in.readString();
        this.returnDate = in.readString();
        this.packageType = in.readString();
        this.destination = in.readString();
        this.duration = in.readString();
        this.additionInsured = in.readString();
        this.jobDeclaration = in.readString();
        this.adult = in.readString();
        this.child = in.readString();
        this.groupType = in.readString();
        this.isSmoking = in.readString();
        this.additionData = in.readString();
        this.polisHolderZipCode = in.readString();
        this.carBrand = in.readString();
        this.carSeries = in.readString();
        this.insuredAddress = in.readString();
        this.insuredCity = in.readString();
        this.insuredProvince = in.readString();
        this.occupation = in.readString();
        this.keuskupan = in.readString();
        this.paroki = in.readString();
        this.lingkungan = in.readString();
        this.familyRole = in.readString();
        this.family_role_other = in.readString();
        this.jobOther = in.readString();
        this.protectionType = in.readString();
        this.addressKtp = in.readString();
        this.paymentType = in.readString();
        this.relation = in.readString();
        this.categoryIndex = in.readInt();
        this.typeGroup = in.readString();
        this.type = in.readString();
        this.departure = in.readString();
        this.komunitas = in.readString();
        this.gadget_type = in.readString();
        this.gadget_name = in.readString();
        this.gadget_brand = in.readString();
        this.gadget_age = in.readString();
        this.gadget_price = in.readString();
        this.serial_number = in.readString();
        this.imei = in.readString();
        this.isAgent = in.readString();
        this.tripReason = in.readString();
        this.codeBooking = in.readString();
        this.detailFlight = in.readString();
        this.departFlightNumber = in.readString();
        this.returnFlightNumber = in.readString();
        this.returnCodeBooking = in.readString();
        this.returnDestination = in.readString();
        this.returnDepartureDate = in.readString();
        this.arrivalDate = in.readString();
        this.ticketPrice = in.readString();
        this.returnTicketPrice = in.readString();
        this.returnDeparture = in.readString();
        this.returnArrivalDate= in.readString();
        this.partnerWePlusId = in.readString();
        this.nik = in.readString();
        this.carPassenger=in.readInt();
    }

    public static final Parcelable.Creator<DataTertanggungRequest> CREATOR = new Parcelable.Creator<DataTertanggungRequest>() {
        @Override
        public DataTertanggungRequest createFromParcel(Parcel source) {
            return new DataTertanggungRequest(source);
        }

        @Override
        public DataTertanggungRequest[] newArray(int size) {
            return new DataTertanggungRequest[size];
        }
    };

    public InsuredUser toInsuredUser(){
        return new InsuredUser(
                0.0f,
                this.fullname,
                phone,
                email,
                address,
                dob,
                sex,
                zipCode,
                province,
                city,
                idNo
        );
    }
}

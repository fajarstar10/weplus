package id.weplus.net;

import java.util.HashMap;
import java.util.Map;

import id.weplus.Query.LoginQuery;
import id.weplus.Query.OtpQuery;
import id.weplus.Query.RegisterConfirmQuery;
import id.weplus.Query.RegisterQuery;
import id.weplus.Query.ResetOtpQuery;
import id.weplus.Query.ResetPasswordQuery;
import id.weplus.model.request.BillInquiryRequest;
import id.weplus.model.request.BillPaymentProductRequest;
import id.weplus.model.request.BillTransactionRequest;
import id.weplus.model.request.BuyPolisRequest;
import id.weplus.model.request.CarProductListRequest;
import id.weplus.model.request.ChangePasswordRequest;
import id.weplus.model.request.ChangePhoneRequest;
import id.weplus.model.request.CheckFlightRequest;
import id.weplus.model.request.CriticalllnessProductListRequest;
import id.weplus.model.request.DataTertanggungRequest;
import id.weplus.model.request.GadgetProductRequest;
import id.weplus.model.request.HealthProductListRequest;
import id.weplus.model.request.LifeProductListRequest;
import id.weplus.model.request.MotorProductListRequest;
import id.weplus.model.request.OvoPaymentRequest;
import id.weplus.model.request.PaymentMethodRequest;
import id.weplus.model.request.PaymentNotificationRequest;
import id.weplus.model.request.ProductListRequest;
import id.weplus.model.request.RefundRequest;
import id.weplus.model.request.TravelProductListRequest;
import id.weplus.model.request.UpdateFcmRequest;
import id.weplus.model.request.UpdateProfileRequest;
import id.weplus.model.request.WithdrawSaldoRequest;
import id.weplus.model.response.BengkelAreaResponse;
import id.weplus.model.response.BengkelPartnerResponse;
import id.weplus.model.response.BillTransactionResponse;
import id.weplus.model.response.GetPartnerDetailResponse;
import id.weplus.model.response.PaymentNotificationResponse;
import id.weplus.model.response.WithdrawSaldoResponse;
import id.weplus.model.response.affiliasirumahsakit.AffiliasiRsResponse;
import id.weplus.model.response.affiliasirumahsakit.RsOptionResponse;
import id.weplus.model.response.afiliasibengkel.AffiliasiBengkelResponse;
import id.weplus.model.response.agent.AgentResponse;
import id.weplus.model.response.agent.saldo.AgentSaldoResponse;
import id.weplus.model.response.agent.transaction.AgentTransactionListResponse;
import id.weplus.model.response.gadget.GadgetBrandResponse;
import id.weplus.model.response.gadget.GadgetCategoryResponse;
import id.weplus.model.response.gadget.GadgetListResponse;
import id.weplus.model.response.insureduser.InsuredUser;
import id.weplus.model.response.insureduser.InsuredUserResponse;
import id.weplus.model.response.mudik.MudikSimasResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {

    @POST("register/")
    @Headers("Content-Type: application/json")
    Call<String> register(@Body RegisterQuery registerQuery);

    @POST("login/")
    @Headers("Content-Type: application/json")
    Call<String> login(@Body LoginQuery loginQuery);

    @POST("product/?page=1/")
    @Headers("Content-Type: application/json")
    Call<String> getProductList();

    @POST("register/confirmation/")
    @Headers("Content-Type: application/json")
    Call<String> cekOtp(@Body OtpQuery otpQuery);

    @POST("otp/reset/")
    @Headers("Content-Type: application/json")
    Call<String> resetOtp(@Body ResetOtpQuery resetOtpQuery);

    @POST("register/confirmation/")
    @Headers("Type: Bearer token")
    Call<String> token(@Body OtpQuery otpQuery);

    @POST("reset-password/")
    Call<String> resetPassword(@Body ResetPasswordQuery resetPasswordQuery);

    @POST("register/confirmation/")
    Call<String> registerConfirm(@Body RegisterConfirmQuery registerConfirmQuery);

    @GET("insured/{id}/")
    Call<String> getInsuredDetail(@Path("id") String id);

    @GET("insureds/{page}/?option={val}")
    Call<String> getInsuredList(@Path("page") int page, @Path("val") String option);

    @POST("payment/")
    @Headers("Content-Type: application/json")
    Call<String> getPaymentMethod(@Body PaymentMethodRequest request);

    @POST("bill-payment/product/")
    @Headers("Content-Type: application/json")
    Call<String> getBillPaymentProduct(
            @Body BillPaymentProductRequest bill
    );

    @GET("bill-payment/category/")
    @Headers("Content-Type: application/json")
    Call<String> getBillPaymentCategory();

    @GET("bill-payment/transaction/")
    @Headers("Content-Type: application/json")
    Call<String> getBillPaymentTransaction(
            @Query("history") int history,
            @Query("page") int page
    );

    @POST("bill-payment/inquiry/")
    @Headers("Content-Type: application/json")
    Call<String> sendBillInquiry(
            @Body BillInquiryRequest bill
    );

    @POST("bill-payment/transactions/")
    @Headers("Content-Type: application/json")
    Call<String> sendBillTransaction(
            @Body BillTransactionRequest bill
    );

    @GET("bill-payment/transaction/{id}/")
    @Headers("Content-Type: application/json")
    Call<BillTransactionResponse> getBillDetail(
            @Path("id") String id
    );

    @POST("transaction/")
    @Headers("Content-Type: application/json")
    Call<String> insertTransaction(@Body DataTertanggungRequest request);

    @GET("transaction/{id}/")
    Call<String> getTransactionDetail(@Path("id") int id);

//    @GET("transactions/{page}?option={val}")
//    @Headers("Content-Type: application/json")
//    Call<String> getTransactionList(@Path("page") int page, @Path("val") String option);

    @GET("transaction/")
    @Headers("Content-Type: application/json")
    Call<String> getTransactionList(
            @Query("history") int history,
            @Query("page") int page
    );

    @GET("agent/transaction/")
    @Headers("Content-Type: application/json")
    Call<String> getAgentTransactionList(
            @Query("history") int history,
            @Query("page") int page
    );

    /**
     * fixed from crash on fragmentTransaksi.java
     **/
    @GET("transactions/{page}")
    @Headers("Content-Type: application/json")
    Call<String> getTransactionList(
            @Path("page") int page,
            @Query("option") String val
    );


    @GET("home/")
    @Headers("Content-Type: application/json")
    Call<String> getHome();

    @GET("buy-polis/")
    @Headers("Content-Type: application/json")
    Call<String> getBuyPolis();

    @GET("buy-polis/")
    Call<String> getAgentBuyPolis(
            @HeaderMap HashMap<String, String> header,
            @Query("is_agent") int i
    );

    @POST("product/")
    @Headers("Content-Type: application/json")
    Call<String> getProductList(
            @Query("page") int i,
            @Body ProductListRequest request
    );

    @POST("product/")
    @Headers("Content-Type: application/json")
    Call<String> getMotorProductList(
            @Query("page") int i,
            @Body MotorProductListRequest request
    );

    @POST("product/")
    @Headers("Content-Type: application/json")
    Call<String> getCarProductList(
            @Query("page") int i,
            @Body CarProductListRequest request
    );

    @GET("car/brand/")
    @Headers("Content-Type: application/json")
    Call<String> getCarBrand();


    @GET("category/filter/5/")
    @Headers("Content-Type: application/json")
    Call<String> getCarFilter();


    @GET("car/brand/{brand}/year/{year}/type/")
    @Headers("Content-Type: application/json")
    Call<String> getCarTypeAndPrice(
            @Path("brand") String brand,
            @Path("year") int year
    );

    @GET("motorcycle/brand/")
    @Headers("Content-Type: application/json")
    Call<String> getMotorBrand();

    @GET("category/filter/{id}/")
    @Headers("Content-Type: application/json")
    Call<String> getMotorFilter(
            @Path("id") int catId
    );

    @GET("motorcycle/brand/{brand}/year/{year}/type/")
    @Headers("Content-Type: application/json")
    Call<String> getMotorTypeAndPrice(
            @Path("brand") String brand,
            @Path("year") int year
    );

    @GET("category/filter/2/")
    @Headers("Content-Type: application/json")
    Call<String> getHealthFilter();

    @POST("product/")
    @Headers("Content-Type: application/json")
    Call<String> getHealthProductList(
            @Query("page") int i,
            @Body HealthProductListRequest request
    );

    @POST("product/")
    @Headers("Content-Type: application/json")
    Call<String> getCriticalProductList(
            @Query("page") int i,
            @Body CriticalllnessProductListRequest request
    );

    @POST("product/")
    @Headers("Content-Type: application/json")
    Call<String> getTravelProductList(
            @Query("page") int i,
            @Body TravelProductListRequest request
    );

    @GET("product/{id}/")
    @Headers("Content-Type: application/json")
    Call<String> getProductDetail(
            @Path("id") int i
    );

    @GET("partner/")
    @Headers("Content-Type: application/json")
    Call<String> getPartnerList();

    @GET("partner/check-employee/")
    @Headers("Content-Type: application/json")
    Call<String> getCheckEmployee(
            @Header("partner") int partnerId,
            @Header("nik") String nik
    );

    @GET("partner/{id}/category/")
    @Headers("Content-Type: application/json")
    Call<String> getCategoryForPartner(
            @Path("id") int partnerId,
            @Header("nik") String nik
    );

    @GET("product/214/")
    @Headers("Content-Type: application/json")
    Call<String> getProductDetail();

    @GET("voucher/")
    @Headers("Content-Type: application/json")
    Call<String> getVoucherList(
            @Query("page") int page
    );

    @GET("voucher/{id}/")
    @Headers("Content-Type: application/json")
    Call<String> getVoucherDetail(
            @Path("id") int id
    );

    @GET("insured/")
    @Headers("Content-Type: application/json")
    Call<String> getInsuredList(
            @Query("history") int history
    );

    @GET("region/country/")
    @Headers("Content-Type: application/json")
    Call<String> getCountryList();

    @GET("region/province/")
    @Headers("Content-Type: application/json")
    Call<String> getProvinceList();

    @GET("region/city/")
    @Headers("Content-Type: application/json")
    Call<String> getCityList();

    @GET("region/province/{id}/city/")
    @Headers("Content-Type: application/json")
    Call<String> getCityListByProvince(@Path("id") String id);

    @GET("region/city/{id}/kecamatan/")
    @Headers("Content-Type: application/json")
    Call<String> getDistrictListByCity(@Path("id") String id);

    @GET("region/kecamatan/{id}/kelurahan/")
    @Headers("Content-Type: application/json")
    Call<String> getSubDistrictListByDistrict(@Path("id") String id);

    @GET("category/filter/7/")
    @Headers("Content-Type: application/json")
    Call<String> getTravelFilter();

    @POST("product/")
    @Headers("Content-Type: application/json")
    Call<String> getLifeProductList(
            @Query("page") int i,
            @Body LifeProductListRequest request
    );

    @GET("category/relation/heir/")
    @Headers("Content-Type: application/json")
    Call<String> getRelationHeir();

    @GET("category/relation/category/{category_id}/partner/{partner_id}/")
    @Headers("Content-Type: application/json")
    Call<String> getPartnerRelation(
            @Path("category_id") String catId,
            @Path("partner_id") String partnerId
    );

    @POST("user/password/")
    @Headers("Content-Type: application/json")
    Call<String> changePassword(@Body ChangePasswordRequest request);

    @POST("user/phone/")
    @Headers("Content-Type: application/json")
    Call<String> changePhone(@Body ChangePhoneRequest request);

    @POST("user/")
    @Headers("Content-Type: application/json")
    Call<String> updateUser(@Body UpdateProfileRequest request);

    @GET("category/form-param/category/{catId}/partner/{partnerId}/")
    @Headers("Content-Type: application/json")
    Call<String> getBSKYFormParam(
            @Path("catId") int catId,
            @Path("partnerId") int partnerId
    );

    @GET("category/bksy/paroki/bksy_keuskupan/{keuskupanId}/")
    @Headers("Content-Type: application/json")
    Call<String> getParokiList(
            @Path("keuskupanId") int keuskupanId
    );

    @GET("category/bksy/lingkungan/bksy_paroki/{parokiId}/")
    @Headers("Content-Type: application/json")
    Call<String> getBskyEnvironmentList(
            @Path("parokiId") int parokiId
    );

    @GET("clinic/")
    @Headers("Content-Type: application/json")
    Call<String> getClinicList();

    @POST("category/flight-checker/")
    @Headers("Content-Type: application/json")
    Call<String> checkFlight(
            @Body CheckFlightRequest request
    );

    @Multipart
    @POST("upload/")
    Call<String> uploadImage(
            @Part MultipartBody.Part photo,
            @PartMap Map<String, RequestBody> text
    );

    @GET("bengkel/partner/")
    Call<BengkelPartnerResponse> getBengkelPartner();

    @GET("hospital/option/")
    Call<RsOptionResponse> getHospitalOptions();


    @GET("hospital/partner/{partner}/city/{city}/")
    Call<AffiliasiRsResponse> getAffiliasiRs(
            @Path("partner") String partner,
            @Path("city") String city
    );

    @GET("bengkel/partner/{id}/area/")
    Call<BengkelAreaResponse> getBengkelArea(
            @Path("id") String partner
    );

    @GET("bengkel/partner/{partner}/area/{area}/")
    Call<AffiliasiBengkelResponse> getAffiliasiBengkel(
            @Path("partner") String partner,
            @Path("area") String area
    );

    @GET("buy-polis/partner/{id}/")
    Call<GetPartnerDetailResponse> getPartnerInformation(
            @Path("id") String partnerId
    );

    @GET("agent/dashboard/")
    Call<AgentResponse> getAgentDashboard();

    @GET("agent/saldo/")
    Call<AgentSaldoResponse> getAgentSaldo(
            @Query("page") int page
    );

    @POST("refund/")
    Call<String> refund(
            @Body RefundRequest request
    );

    @POST("payment/notification/{orderCode}/")
    Call<PaymentNotificationResponse> sendPaymentNotification(
            @Path("orderCode") String orderCode,
            @Body PaymentNotificationRequest request
    );

    @GET("gadget/brand/{gadgetType}/")
    Call<GadgetBrandResponse> getGadgetBrand(
            @Path("gadgetType") String type
    );

    @GET("category/filter/15/")
    @Headers("Content-Type: application/json")
    Call<GadgetCategoryResponse> getGadgetFilter();

    @GET("gadget/type/{type}/brand/{brand}/")
    Call<GadgetListResponse> getGadgetList(
            @Path("type") String type,
            @Path("brand") String brand
    );

    @POST("product/")
    @Headers("Content-Type: application/json")
    Call<String> getGadgetProductList(
            @Query("page") int i,
            @Body GadgetProductRequest request
    );

    @POST("user/fcm-token/")
    @Headers("Content-Type: application/json")
    Call<String> updateFcmToken(
            @Body UpdateFcmRequest updateFcmRequest
    );

    @POST("agent/saldo/")
    @Headers("Content-Type: application/json")
    Call<WithdrawSaldoResponse> withdrawSaldo(
            @Body WithdrawSaldoRequest body
    );

    @GET("agent/transaction/")
    @Headers("Content-Type: application/json")
    Call<AgentTransactionListResponse> getAgentTransactions(
            @Query("page") int page
    );


    @GET("agent/insured/")
    @Headers("Content-Type: application/json")
    Call<String> getAgentInsuredList(
            @Query("page") int history
    );

    @GET("agent/transaction/{id}/")
    Call<String> getAgentTransactionDetail(@Path("id") int id);


    @POST("xendit/transaction/")
    @Headers("Content-Type: application/json")
    Call<String> sendOvoPayment(
            @Body OvoPaymentRequest body
    );

    @GET("category/form-param/category/10/partner/11/")
    @Headers("Content-Type: application/json")
    Call<MudikSimasResponse> getSimasMudikFilter();

    @GET("user/insured/")
    @Headers("Content-Type: application/json")
    Call<InsuredUserResponse> getInsuredUsers(
            @Query("page") int page
    );

    @POST("user/insured/")
    @Headers("Content-Type: application/json")
    Call<String> addInsuredUser(
            @Body InsuredUser insuredUser
    );

    @POST("user/insured/{id}/")
    @Headers("Content-Type: application/json")
    Call<String> updateInsuredUser(
            @Path("id") String id,
            @Body InsuredUser insuredUser
    );

//    @GET("product/{page}?option={val}")
//    Call<String> getProductList(@Path("page") int page, @Path("val") String option);

//    @GET("voucher/{id}")
//    Call<String> getVoucherDetail(@Path("page") int page, @Path("val") String option);
//
//    @GET("voucher/{page}?option={val}")
//    Call<String> getVoucherList(@Path("page") int page, @Path("val") String option);


}

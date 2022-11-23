package okhttp3;

import config.Provider;
import dto.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class AddNewContact {
    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZ2Vuc2l0c2theWFAYmsucnUiLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY2OTY1NDQ3MSwiaWF0IjoxNjY5MDU0NDcxfQ.RYo7A7JtUYtExySVo1Bi5gRYatVTzHBbgeE0nL1rMo0";


    @Test
    public void AddNewContactSuccess() throws IOException {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;
        ContactDto addContact= ContactDto.builder().name("Olga"+i).lastName("Ivanov"+i).email("ret"+i+"@mail.com").phone("1335128"+i).address("Tel Aviv").description("Best Friend").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(addContact),Provider.getInstance().getJson());

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization",token)
                .post(body)
                .build();
        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
       SuccessDto successDto= Provider.getInstance().getGson().fromJson(response.body().string(),SuccessDto.class);
        System.out.println(successDto);


    }
    @Test
public void AddNewContactWithWrongPhone() throws IOException {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;
        ContactDto addContact= ContactDto.builder().name("Olga"+i).lastName("Ivanov"+i).email("ret"+i+"@mail.com").phone("138").address("Tel Aviv").description("Best Friend").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(addContact),Provider.getInstance().getJson());

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization",token)
                .post(body)
                .build();
        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);
        ErrorDto errorDto = Provider.getInstance().getGson().fromJson(response.body().string(), ErrorDto.class);
        Object message= errorDto.getMessage().toString();
        Assert.assertEquals(message,"{phone=Phone number must contain only digits! And length min 10, max 15!}");
        Assert.assertEquals(errorDto.getStatus(),400);
    }
    @Test
    public void AddNewContactUnauthorized() throws IOException {
        int i = (int) (System.currentTimeMillis() / 1000) % 3600;

        ContactDto addContact= ContactDto.builder().name("Olga"+i).lastName("Ivanov"+i).email("ret"+i+"@mail.com").phone("1335128"+i).address("Tel Aviv").description("Best Friend").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(addContact),Provider.getInstance().getJson());

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization","akkkk")
                .post(body)
                .build();
        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);
        ErrorDto errorDto = Provider.getInstance().getGson().fromJson(response.body().string(), ErrorDto.class);
        Object message= errorDto.getMessage().toString();
        Assert.assertEquals(message,"JWT strings must contain exactly 2 period characters. Found: 0");
        Assert.assertEquals(errorDto.getStatus(),401);
    }
@Test
    public void AddDuplicateContactField(){
        //BUG
}
    }


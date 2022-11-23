package okhttp3;

import config.Provider;
import dto.AuthRequestDto;
import dto.ContactDto;
import dto.GetAllContactsDto;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class AddNewContact {
    String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiZ2Vuc2l0c2theWFAYmsucnUiLCJpc3MiOiJSZWd1bGFpdCIsImV4cCI6MTY2OTY1NDQ3MSwiaWF0IjoxNjY5MDU0NDcxfQ.RYo7A7JtUYtExySVo1Bi5gRYatVTzHBbgeE0nL1rMo0";

    @Test
    public void AddNewContactSuccess() throws IOException {
        AuthRequestDto auth= AuthRequestDto.builder().username("gensitskaya@bk.ru").password("Od123456$").build();
        RequestBody body = RequestBody.create(Provider.getInstance().getGson().toJson(auth),Provider.getInstance().getJson());
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization",token)
                .post(body)
                .build();
        Response response = Provider.getInstance().getClient().newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

       
        }

    }


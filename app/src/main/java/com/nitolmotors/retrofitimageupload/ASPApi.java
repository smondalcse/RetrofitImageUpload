package com.nitolmotors.retrofitimageupload;

public class ASPApi {

    /*


    using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;

namespace ImageUpload.Controller
{
    [RoutePrefix("api/controller")]

    public class FileUploadController : ApiController
    {
        [Route("Test")]
        [HttpGet]
        public string Test()
        {
            return "OK";
        }

        [Route("upload")]
        [HttpPost]
        public HttpResponseMessage Upload()
        {
            try
            {
                var requset = HttpContext.Current.Request;
                var description = requset.Form["description"];
                var photo = requset.Files["photo"];
                photo.SaveAs(HttpContext.Current.Server.MapPath("~/Content/Uploads/" + photo.FileName));
                return new HttpResponseMessage(HttpStatusCode.OK);
            } catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest);
            }
        }

    }
}




     */


}

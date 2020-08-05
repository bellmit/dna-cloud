import request from "../xhr/upload"

class upload {
  codisUpload(info) {
    //删除贡献者
    return request.xhr({
      url: "api_iLabSTRmix/mix/codis/uploadCodis",
      data: info,
      type: "post"
    });
  }
}
export default new upload();

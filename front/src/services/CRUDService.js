import axios from "axios";

export default class CRUDService {

  _getRequest(endPoint, headers, context) {
    var params = [];
    if (context != undefined) {
      if (context.sort != undefined) {

        params.push({
          name: "sortBy",
          value: context.sort.by == "partNumber" ? "PART_NUMBER" : context.sort.by.toUpperCase()
        });

        params.push({
          name: "sortOrder",
          value: (context.sort.desc ? "DESC" : "ASC")
        });
      }
      if (context.currentPage != undefined) {

        params.push({
          name: "page",
          value: context.currentPage
        });
      }
      if (context.perPage != undefined) {

        params.push({
          name: "pageSize",
          value: context.perPage
        });
      }
      if (context.filters != undefined) {

        for (var key in context.filters) {
          if (context.filters[key].length > 0) {
            params.push({
              name: key,
              value: context.filters[key]
            });
          }
        }
      }
    }
    console.log("get request", this.createRequest(endPoint, params));
    return axios.get(
      this.createRequest(endPoint, params),
      {
        headers: headers
      })
      .then(response => {
        console.log(response);
        if (context != undefined) {
          context.totalRows = response.data.total;
          context.size = response.data.items.length;
          // context.totalPages = response.data.totalPages;
          // context.currentPage = response.data.number;
        }
        console.log("response", response);
        return response.data.items == undefined ? response.data : response.data.items;
      });
  }

  _putRequest(endPoint, headers, body, params) {
    return axios.put(
      this.createRequest(endPoint, params),
      body
    );
  }

  _postRequest(endPoint, headers, body, params) {
    console.log("post", body);
    return axios.post(
      this.createRequest(endPoint, params),
      body,
      {
        headers: headers
      }
    );
  }

  _deleteRequest(endPoint, headers, context) {
    return axios.delete(
      this.createRequest(endPoint + "/" + context.entityId), {
      headers: headers
    })
  }


  constructor() { }

  get accessHeader() {
    return {};
    // return {
    //   "Authorization": "Bearer " + this._connection.accessToken
    // };
  }

  get suppliesEndPoint() {
    return "/sad/api/supplies";
  }

  get detailsEndPoint() {
    return "/sad/api/parts";
  }

  get suppliersEndPoint() {
    return "/sad/api/suppliers";
  }

  get suppliedDetailsEndPoint() {
    return "/sad/api/parts/supplied";
  }

  get parallelsEndPoint() {
    return "/sad/api/parallels";
  }

  get usersEndPoint() {
    return "/sad/api/users";
  }

  createRequest(address, params) {
    var request = address;
    if (params != undefined && params.length > 0) {
      request = request + "?";
      var requestParams = "";
      for (let i = 0; i < params.length; i++) {
        requestParams = requestParams + "&" + params[i].name + "=" + params[i].value;
      }
      request = request + requestParams.substring(1);
    }
    return request;
  }

  getSupply(entityId) {
    return this._getRequest(
      this.suppliesEndPoint + "/" + entityId
    ).then(item =>{
      item.date = item.date.split('T')[0];
      return item;
    })
  }

  getSupplier(entityId) {
    return this._getRequest(
      this.suppliersEndPoint + "/" + entityId
    )
  }

  getDetail(entityId) {
    return this._getRequest(
      this.detailsEndPoint + "/" + entityId
    )
  }

  getSuppliedDetail(entityId) {
    return this._getRequest(
      this.suppliedDetailsEndPoint + "/" + entityId
    ).then(item =>{
      item.date = item.date.split('T')[0];
      return item;
    })
  }

  getEmptySupplier() {
    return {
      name: "[Данные удалены]",
      address: "[Данные удалены]",
      phone: "[Данные удалены]"
    }
  }

  getEmptyDetail() {
    return {
      name: "[Данные удалены]",
      partNumber: "[Данные удалены]",
      note: "[Данные удалены]"
    }
  }

  getEmptySuppliedDetail() {
    return {
      amount: 0,
      supplierData: this.getEmptySupplier(),
      detailData: this.getEmptyDetail()
    }
  }
  getSupplies(context) {
    return this._getRequest(
      this.suppliesEndPoint,
      this.accessHeader,
      context).then(async items => {
        return await this.getSuppliedDetails({perPage : 100}).then(suppliedDetails => {
          items.forEach(element => {
            let suppliedDetailData = suppliedDetails.find(sd => sd.entityId == element.suppliedParts);
            element.suppliedDetailData = suppliedDetailData != null ? suppliedDetailData : this.getEmptySuppliedDetail();
            element.date = element.date.split('T')[0];
          })
          return items;
        });
      });
  }

  getDetails(context) {
    return this._getRequest(
      this.detailsEndPoint,
      this.accessHeader,
      context);
  }

  getSuppliedDetails(context) {
    return this._getRequest(
      this.suppliedDetailsEndPoint,
      this.accessHeader,
      context).then(async items => {
        let suppliersPromis = this.getSuppliers({perPage : 100}).then(suppliers => {
          items.forEach(element => {
            let supplierData = suppliers.find(s => s.entityId == element.supplier);
            element.supplierData = supplierData != null ? supplierData : this.getEmptySupplier();
            element.date = element.date.split('T')[0];
          });
        });

        let detailsPromis = this.getDetails({perPage : 100}).then(details => {
          items.forEach(element => {
            let detailData = details.find(d => d.entityId == element.part);
            element.detailData = detailData != null ? detailData : this.getEmptyDetail();
          });
        });
        await Promise.all([suppliersPromis, detailsPromis]);
        return items;
      });
  }

  getSuppliers(context) {
    return this._getRequest(
      this.suppliersEndPoint,
      this.accessHeader,
      context);
  }

  getParallels(context) {
    return this._getRequest(
      this.parallelsEndPoint,
      this.accessHeader,
      context);
  }

  getUsers(context) {
    return this._getRequest(
      this.usersEndPoint + "/list",
      this.accessHeader,
      context);
  }

  updateSupply(supplyId, newData) {
    return this._putRequest(
      this.suppliesEndPoint,
      this.accessHeader,
      {
        id: newData.id,
        entityId: newData.entityId,
        suppliedParts: newData.suppliedParts,
        amount: newData.amount,
        date: newData.date + "T08:19:07.715"
      }
    );
  }

  updateDetail(detailId, newData) {
    console.log("updateDetail", newData);
    return this._putRequest(
      this.detailsEndPoint,
      this.accessHeader,
      newData
    );
  }

  updateSuppliedDetail(studentId, newData) {
    return this._putRequest(
      this.suppliedDetailsEndPoint,
      this.accessHeader,
      {
        id: newData.id,
        entityId: newData.entityId,
        cost: newData.cost,
        part: newData.part,
        supplier: newData.supplier,
        date: newData.date + "T08:19:07.715"
      }
    );
  }

  updateSupplier(supplierId, newData) {
    console.log("updateSupplier", newData);
    return this._putRequest(
      this.suppliersEndPoint,
      this.accessHeader,
      newData
    );
  }

  updateUser(userId, newData) {
    return this._putRequest(
      this.usersEndPoint,
      this.accessHeader,
      newData
    );
  }

  addSupply(newData) {
    return this._postRequest(
      this.suppliesEndPoint,
      this.accessHeader,
      {
        amount: Number(newData.amount),
        suppliedParts: newData.suppliedParts,
        date: newData.date + "T08:19:07.715"
      }
    );
  }

  addDetail(newData) {
    return this._postRequest(
      this.detailsEndPoint,
      this.accessHeader,
      newData
    );
  }

  addSuppliedDetail(newData) {
    return this._postRequest(
      this.suppliedDetailsEndPoint,
      this.accessHeader,
      {
        cost: Number(newData.cost),
        part: newData.part,
        supplier: newData.supplier,
        date: newData.date + "T08:19:07.715"
      }
    );
  }

  addSupplier(newData) {
    return this._postRequest(
      this.suppliersEndPoint,
      this.accessHeader,
      newData
    );
  }

  addUser(newData) {
    return this._postRequest(
      this.usersEndPoint,
      this.accessHeader,
      newData,
      []
    );
  }

  removeSupply(entityId) {
    return this._deleteRequest(
      this.suppliesEndPoint,
      this.accessHeader,
      {
        entityId: entityId
      }
    );
  }

  removeDetail(entityId) {
    return this._deleteRequest(
      this.detailsEndPoint,
      this.accessHeader,
      {
        entityId: entityId
      }
    );
  }

  removeSuppliedDetail(entityId) {
    return this._deleteRequest(
      this.suppliedDetailsEndPoint,
      this.accessHeader,
      {
        entityId: entityId
      }
    );
  }

  removeSupplier(entityId) {
    return this._deleteRequest(
      this.suppliersEndPoint,
      this.accessHeader,
      {
        entityId: entityId
      }
    );
  }

  removeUser(entityId) {
    return this._deleteRequest(
      this.usersEndPoint,
      this.accessHeader,
      {
        entityId: entityId
      }
    );
  }

  deletedSuffix(data) {
    if (data == null || data == undefined) {
      return "[удален]";
    }
    return "";//data.isDeleted == true ? "[удален]" : "";
  }

  supplyPresenter(data) {
    if (data == null || data == undefined) {
      return "Отсутствует";
    }
    var suppliedDetail = null;
    this.getSupply(data.suppliedDetail).then(item => suppliedDetail = item);

    return suppliedDetail + data.name + this.deletedSuffix(data);
  }

  classPresenter(data) {
    if (data == null || data == undefined) {
      return "Отсутствует";
    }
    return data.parallel.parallelNumber + " '" + data.letter + "' " + data.studyYear.name + this.deletedSuffix(data);
  }

  studentPresenter(data) {
    if (data == null || data == undefined) {
      return "Отсутствует";
    }
    return this.teacherPresenter(data) + " " + this.classPresenter(data.course) + ")" + this.deletedSuffix(data);
  }

  teacherPresenter(data) {
    if (data == null || data == undefined) {
      return "Отсутствует";
    }
    return data.secondName + " " + data.firstName + " " + data.patronymic + this.deletedSuffix(data);
  }

  parallelPresenter(data) {
    if (data == null || data == undefined) {
      return "Отсутствует";
    }
    return data.parallelNumber + this.deletedSuffix(data);
  }

  userPresenter(data) {
    return data.username + this.deletedSuffix(data);
  }

  isAdmin(user) {
    return user.roles.includes(this._adminRole);
  }

  isModerator(user) {
    return user.roles.includes(this._moderatorRole);
  }
}
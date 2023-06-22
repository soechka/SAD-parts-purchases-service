export default class MessageService {
  _toast = null;
  constructor(toast) {
    this._toast = toast;
  }

  show(title, message) {
    this._toast.toast(message, {
      title: title,
      autoHideDelay: 5000,
      appendToast: true,
    });
  }

  showError(errorMessage) {
    this._toast.toast(errorMessage, {
      title: "Произошла ошибка",
      autoHideDelay: 5000,
      appendToast: true,
      variant: "danger",
    });
  }

  showDelete(message) {
    this.show("Удаление", "Запсиь о " + message + " удалена");
  }

  showCreate(message) {
    this.show("Создание", "Запсиь о " + message + " создана");
  }

  showUpdate(message) {
    this.show("Обновление", "Запсиь о " + message + " обновлена");
  }
}
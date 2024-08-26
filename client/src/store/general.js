import { makeObservable, observable, action } from 'mobx';

class GeneralStore {
  id = Math.round(Math.random() * 100000);
  title = "default";

  constructor() {
    makeObservable(this, {
      title: observable,
      changeTitle: action,
    });
  }

  // reducers
  get state() {
    return {
      id: this.id,
      title: this.title,
    }
  }

  // actions
  changeTitle = (str) => this.title = str;

}

export default GeneralStore;
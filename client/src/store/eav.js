import { action, makeObservable, observable, toJS } from 'mobx';

class EavStore {
  loading = false;
  connected = false;
  entityTypes = [];
  entities = [];
  values = [];

  constructor() {
    makeObservable(this, {
      loading: observable,
      connected: observable,
      entityTypes: observable,
      entities: observable,
      values: observable,
      _setLoading: action,
      _setConnected: action,
      _setEntityTypes: action,
      _setEntities: action,
      _setValues: action,
      clearEntityTypes: action,
      clearEntities: action,
      clearValues: action,
    });
  }

  // reducers
  get state() {
    return {
      loading: this.loading,
      connected: this.connected,
      entityTypes: toJS(this.entityTypes),
      entities: toJS(this.entities),
      values: toJS(this.values),
    }
  }

  // async actions
  connect = async () => {
    try {
      const res = await fetch("http://localhost:4000/connect", {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          host: "localhost:3306",
          dbName: "localdb",
          user: "root",
          password: "password",
        })
      }).then(x => x.text());
      if (res !== "OK") throw new Error(res);
      this._setConnected(true);
    } catch (e) {
      console.error("Connection failed -", e);
      this._setConnected(false);
    }
  }

  fetchEntityTypes = async () => {
    this._setLoading(true);
    try {
      const res = await fetch("http://localhost:4000/entity-types", {
        method: "GET",
      }).then(x => x.json());
      this._setEntityTypes(res);
    } catch (e) {
      console.error("API failed -", e);
    }
    this._setLoading(false);
  }

  fetchEntities = async (id) => {
    this._setLoading(true);
    try {
      const res = await fetch("http://localhost:4000/entities/" + id, {
        method: "GET",
      }).then(x => x.json());
      this._setEntities(res);
    } catch (e) {
      console.error("API failed -", e);
    }
    this._setLoading(false);
  }

  fetchValues = async (id) => {
    this._setLoading(true);
    try {
      const res = await fetch("http://localhost:4000/view/entity/" + id, {
        method: "GET",
      }).then(x => x.json());
      this._setValues(res);
    } catch (e) {
      console.error("API failed -", e);
    }
    this._setLoading(false);
  }

  // internal actions
  _setLoading = (x) => this.loading = x;
  _setConnected = (x) => this.connected = x;
  _setEntityTypes = (arr) => this.entityTypes = [ ...arr];
  _setEntities = (arr) => this.entities = [ ...arr];
  _setValues = (arr) => this.values = [ ...arr];
  
  // external actions
  clearEntityTypes = () => this.entityTypes = [];
  clearEntities = () => this.entities = [];
  clearValues = () => this.values = [];
}

export default EavStore;
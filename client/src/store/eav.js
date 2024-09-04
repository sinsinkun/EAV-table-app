import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';

export const connect = createAsyncThunk(
  'eav/connect',
  async (_, { rejectWithValue }) => {
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
      if (res !== "OK") rejectWithValue(false);
      return true;
    } catch (e) {
      console.error("Connection failed -", e);
      return rejectWithValue(false);
    }
  }
)

export const fetchEntityTypes = createAsyncThunk(
  'eav/fetchEntityTypes',
  async (_, { rejectWithValue }) => {
    try {
      const res = await fetch("http://localhost:4000/entity-types", {
        method: "GET",
      }).then(x => x.json());
      return res;
    } catch (e) {
      console.error("API failed -", e);
      return rejectWithValue(null);
    }
  }
)

export const fetchEntities = createAsyncThunk(
  'eav/fetchEntities',
  async (typeId, { rejectWithValue }) => {
    try {
      const res = await fetch("http://localhost:4000/entities/" + typeId, {
        method: "GET",
      }).then(x => x.json());
      return res;
    } catch (e) {
      console.error("API failed -", e);
      return rejectWithValue(null);
    }
  }
)

export const fetchValues = createAsyncThunk(
  'eav/fetchValues',
  async (entityId, { rejectWithValue }) => {
    try {
      const res = await fetch("http://localhost:4000/view/entity/" + entityId, {
        method: "GET",
      }).then(x => x.json());
      return res;
    } catch (e) {
      console.error("API failed -", e);
      return rejectWithValue(null);
    }
  }
)

export const addAttribute = createAsyncThunk(
  'eav/addAttribute',
  async (input, { rejectWithValue }) => {
    try {
      const { attr, valueType, entityType } = input;
      if (!attr || !valueType, !entityType) throw new Error("Missing required inputs");
      const res = await fetch("http://localhost:4000/attribute", {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(input),
      }).then(x => x.json());
      return res;
    } catch (e) {
      console.error("API failed -", e);
      return rejectWithValue(null);
    }
  }
)

export const addEntity = createAsyncThunk(
  'eav/addEntity',
  async (input, { rejectWithValue }) => {
    try {
      const { entity, entityType } = input;
      if (!entity || !entityType) throw new Error("Missing required inputs");
      const res = await fetch("http://localhost:4000/entity", {
        method: "POST",
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(input),
      }).then(x => x.json());
      return res;
    } catch (e) {
      console.error("API failed -", e);
      return rejectWithValue(null);
    }
  }
)

export const eavSlice = createSlice({
  name: 'eav',
  initialState: {
    loading: false,
    connected: false,
    entityTypes: [],
    entities: [],
    values: [],
    formType: null,
    activeEnType: null,
  },
  reducers: {
    clearEntityTypes: (state) => {
      state.entityTypes = []
    },
    clearEntities: (state) => {
      state.entities = []
    },
    clearValues: (state) => {
      state.values = []
    },
    openForm: (state, action) => {
      state.formType = action.payload
    },
    closeForm: (state) => {
      state.formType = null
    },
    setActiveEnType: (state, action) => {
      const [active] = state.entityTypes.filter(x => x.id === action.payload);
      if (active) state.activeEnType = active;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(connect.pending, (state) => {
      state.loading = true;
    }).addCase(connect.fulfilled, (state) => {
      state.loading = false;
      state.connected = true;
    }).addCase(connect.rejected, (state) => {
      state.loading = false;
      state.connected = false;
    });
    builder.addCase(fetchEntityTypes.pending, (state) => {
      state.loading = true;
    }).addCase(fetchEntityTypes.fulfilled, (state, action) => {
      state.loading = false;
      state.entityTypes = action.payload;
    }).addCase(fetchEntityTypes.rejected, (state) => {
      state.loading = false;
      state.entityTypes = [];
    });
    builder.addCase(fetchEntities.pending, (state) => {
      state.loading = true;
    }).addCase(fetchEntities.fulfilled, (state, action) => {
      state.loading = false;
      state.entities = action.payload;
    }).addCase(fetchEntities.rejected, (state) => {
      state.loading = false;
      state.entities = [];
    });
    builder.addCase(fetchValues.pending, (state) => {
      state.loading = true;
    }).addCase(fetchValues.fulfilled, (state, action) => {
      state.loading = false;
      state.values = action.payload;
    }).addCase(fetchValues.rejected, (state) => {
      state.loading = false;
      state.values = [];
    });
    builder.addCase(addAttribute.pending, (state) => {
      state.loading = true;
    }).addCase(addAttribute.fulfilled, (state, action) => {
      state.loading = false;
      if (state.values.length < 1) return;
      const firstValue = state.values[0];
      // build view representation
      const view = {
        entityTypeId: action.payload.entityTypeId,
        entityType: firstValue.entityType,
        entityId: firstValue.entityId,
        entity: firstValue.entity,
        attrId: action.payload.id,
        attr: action.payload.attr,
        valueType: action.payload.valueType,
        allowMultiple: action.payload.allowMultiple,
        valueId: null,
      }
      // push to end
      state.values.push(view);
    }).addCase(addAttribute.rejected, (state) => {
      state.loading = false;
    });
    builder.addCase(addEntity.pending, (state) => {
      state.loading = true;
    }).addCase(addEntity.fulfilled, (state, action) => {
      state.loading = false;
      state.entities.push(action.payload);
    }).addCase(addEntity.rejected, (state) => {
      state.loading = false;
    });
  }
});

export const {
  clearEntityTypes,
  clearEntities,
  clearValues,
  openForm,
  closeForm,
  setActiveEnType,
} = eavSlice.actions;

export default eavSlice.reducer;
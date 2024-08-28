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
      rejectWithValue(false);
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
      rejectWithValue(null);
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
      rejectWithValue(null);
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
      rejectWithValue(null);
    }
  }
)

export const addAttribute = createAsyncThunk(
  'eav/addAttribute',
  async (input, { rejectWithValue }) => {
    // todo
  }
)

export const addEntity = createAsyncThunk(
  'eav/addEntity',
  async (input, { rejectWithValue }) => {
    // todo
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
      if (active) state.activeEnType = active.entityType;
    },
  },
  extraReducers: (builder) => {
    builder.addCase(connect.pending, (state) => {
      state.loading = true;
    })
    builder.addCase(connect.fulfilled, (state) => {
      state.loading = false;
      state.connected = true;
    })
    builder.addCase(connect.rejected, (state) => {
      state.loading = false;
      state.connected = false;
    })

    builder.addCase(fetchEntityTypes.pending, (state) => {
      state.loading = true;
    })
    builder.addCase(fetchEntityTypes.fulfilled, (state, action) => {
      state.loading = false;
      state.entityTypes = action.payload;
    })
    builder.addCase(fetchEntityTypes.rejected, (state) => {
      state.loading = false;
      state.entityTypes = [];
    })

    builder.addCase(fetchEntities.pending, (state) => {
      state.loading = true;
    })
    builder.addCase(fetchEntities.fulfilled, (state, action) => {
      state.loading = false;
      state.entities = action.payload;
    })
    builder.addCase(fetchEntities.rejected, (state) => {
      state.loading = false;
      state.entities = [];
    })

    builder.addCase(fetchValues.pending, (state) => {
      state.loading = true;
    })
    builder.addCase(fetchValues.fulfilled, (state, action) => {
      state.loading = false;
      state.values = action.payload;
    })
    builder.addCase(fetchValues.rejected, (state) => {
      state.loading = false;
      state.values = [];
    })
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
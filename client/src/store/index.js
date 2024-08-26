import EavStore from "./eav";
import GeneralStore from "./general";

const general = new GeneralStore();
const eav = new EavStore();

if (import.meta.env.VITE_STAGE === "dev") {
  window.store = { general, eav };
  console.log("Initialized dev store access", window.store);
}
export { general, eav };
<template>
  <div>
    <Menubar :model="menuItems">
      <template #start>
        <img alt="logo" src="images/logo.png" height="40" class="p-mr-2">
      </template>
    </Menubar>

    <h1>healthchecks without data</h1>
    <div v-for="check in checks" :key="check.id" class="check">
      <div v-if="hasData(check) === false" class="data p-d-flex p-ai-center">
        <Fieldset>
          <template #legend>
            {{ check.name }}
          </template>
          off <InputSwitch v-model="check.checked" @click="toggleHealth(check.name)" /> on
        </Fieldset>
      </div>
    </div>

    <h1>healthchecks with data</h1>
    <div v-for="check in checks" :key="check.id" class="check">
      <div v-if="hasData(check)" class="data p-d-flex p-ai-center">
        <Fieldset>
          <template #legend>
            {{ check.name }}
          </template>
          off <InputSwitch v-model="check.checked" @click="toggleHealth(check.name)" /> on

          <div v-for="(value, name) in check.data" v-bind:key="name" class="datafield">
            <span class="datalabel"
              ><label >{{ name }}</label></span
            >
            <InputText
              class="p-inputtext-sm"
              placeholder="Small"
              type="text"
              v-model="check.data[name]"
            />
            <Button icon="pi  pi-save" class="p-button-rounded" 
            @click="addData(name, check.data[name], check.name)"
            />
            
            <Button v-if="notOne(check.data) === false"
              icon="pi p-button-rounded pi-trash"
              class="p-button-rounded p-button-danger"
              @click="deleteData(name, check.name)"
            />
          </div>
          <div class="datafield">
            <span class="datalabel"
              ><InputText
              class="p-inputtext-sm newdata"
              placeholder="new"
              type="text"
              v-model="check.newData"
            /></span
            >
            <InputText
              class="p-inputtext-sm"
              placeholder="enter new data value"
              type="text"
              v-model="check.newValue"
            />
            <Button
              icon="pi pi-plus"
              class="p-button-rounded"
              @click="addData(check.newData, check.newValue, check.name)"
            />
          </div>
        </Fieldset>
      </div>
    </div>
  </div>
</template>

<script>
import Menubar from "primevue/menubar";
import InputSwitch from "primevue/inputswitch";
import InputText from "primevue/inputtext";
import Fieldset from "primevue/fieldset";
import Button from "primevue/button";

import { ref } from "vue";

export default {
  name: "bucki",
  components: {
    Menubar,
    InputSwitch,
    InputText,
    Button,
    Fieldset,
  },
  setup() {
    const checks = ref([]);
    const menuItems = ref ([]);

    return {
      checks,
      menuItems,
    };
  },
  created() {
    this.menuItems = [
          {
            label:'reload',
            icon:'pi pi-fw pi-refresh',
            command: () => this.getHealth()
          }
        ];
        this.getHealth();    
  },
  methods: {
    getHealth() {
      this.axios
        .get("/q/health")
        .then((resp) => {
          this.checks = this.updateChecks(resp.data.checks);
        })
        .catch((error) => (this.checks = this.updateChecks(error.response.data.checks)));
    },
    toggleHealth(checkName) {
      this.axios.get("/bucki/change/" + checkName).then((resp) => console.log(resp.data));
    },
    notOne(data) {
      let one = false;
       if (Object.keys(data).length == 1) {
         one = true;
       }
       return one;
    },
    hasData(check) {
      let dataExist = false;
      if ("data" in check) {
        dataExist = true;
      }
      return dataExist;
    },
    addData(data, value, check) {
      this.axios
        .get("/bucki/changeData/" + check + "/" + data + "/" + value)
        .then((resp) => {
          if (resp.data == "change done") {
              this.getHealth();
          }
        });
    },
    deleteData(dataName, check) {
      this.axios
        .get("/bucki/deleteData/" + check + "/" + dataName)
        .then((resp) => {
          if (resp.data == "change done") {
              this.getHealth();
          }
        });
    },
    updateChecks(checks) {
      for (var c = 0; c < checks.length; c++) {
        if (checks[c].status == "UP") {
          checks[c]["checked"] = true;
        } else {
          checks[c]["checked"] = false;
        }
        checks[c]["newValue"] = "";
        checks[c]["newData"] = ""
      }
      return checks;
    },
  },
};
</script>

<style>
.check {
  display: inline-flex;
  margin: 2px;
}
.check fieldset.p-fieldset.p-component {
  min-width: 200px;
  min-height: 100px;
}
.data {
  margin-top: 10px;
}
.datafield {
  margin-top: 10px;
}
.datatext {
  width: 30px;
}
.datalabel {
  width: 80px;
  display: inline-block;
}
.newdata {
  width: 75px;
}
</style>

<template>
  <div id="healthHelper">
    <Card v-if="checksWithoutData.length > 0" class="togglecard" style="max-width: 30rem">
      <template #header>
        <h3>healthchecks without data (uri: {{ uri }})</h3>
      </template>
      <template #content>
        <div v-for="check in checksWithoutData" :key="check.id" class="check">
          <div class="data p-d-flex p-ai-center">
            <Fieldset>
              <template #legend>
                {{ check.name }}
              </template>
              down <InputSwitch v-model="check.checked" @click="toggleHealth(check.name)" /> up
            </Fieldset>
          </div>
        </div>
      </template>
    </Card>
    <Card v-if="checksWithData.length > 0" class="togglecard" style="max-width: 85rem">
      <template #header>
        <h3>healthchecks with data (uri: {{ uri }})</h3>
      </template>
      <template #content>
        <div v-for="check in checksWithData" :key="check.id" class="check">
          <div class="databig p-d-flex p-ai-center">
            <Fieldset>
              <template #legend>
                {{ check.name }}
              </template>
              down <InputSwitch v-model="check.checked" @click="toggleHealth(check.name)" /> up

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
      </template>
    </Card>
  </div>
</template>

<script>
import InputSwitch from "primevue/inputswitch";
import InputText from "primevue/inputtext";
import Fieldset from "primevue/fieldset";
import Button from "primevue/button";
import Card from 'primevue/card';

import { ref } from "vue";

export default {
  name: "healthhelper",
  components: {
    InputSwitch,
    InputText,
    Button,
    Fieldset,
    Card,
  },
  props: {
      uri: {type: String}
  },
  setup() {
    const checks = ref([]);
    const checksWithData = ref([]);
    const checksWithoutData = ref([]);

    return {
      checks,
      checksWithData,
      checksWithoutData,
    };
  },
  created() {
    this.getHealth();    
  },
  methods: {
    getHealth() {
      this.axios
        .get(this.uri)
        .then((resp) => {
          this.checks = this.updateChecks(resp.data.checks);
          this.splitChecks();          
        })
        .catch((error) => {
          this.checks = this.updateChecks(error.response.data.checks);
          this.splitChecks();
        });
        
    },
    toggleHealth(checkName) {
      this.axios.get("bucki/change/" + checkName).then((resp) => {
        console.log(resp.data);
        this.$emit("updateHealthHelper");
        }
      );
    },
    splitChecks() {
      this.checksWithData = [];
      this.checksWithoutData = [];
      this.checks.forEach((check) => {
          if (this.hasData(check) === true) {
              this.checksWithData.push(check);
          } else {
              this.checksWithoutData.push(check);
          }
      });
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
        .get("bucki/changeData/" + check + "/" + data + "/" + value)
        .then((resp) => {
          if (resp.data == "change done") {
              this.getHealth();
              this.$emit("updateHealthHelper");
          }
        });
    },
    deleteData(dataName, check) {
      this.axios
        .get("bucki/deleteData/" + check + "/" + dataName)
        .then((resp) => {
          if (resp.data == "change done") {
              this.getHealth();
              this.$emit("updateHealthHelper");
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
body {
  background-color: gray;
}
.check {
  display: inline-flex;
}
.check fieldset.p-fieldset.p-component {
  min-width: 18rem;
  min-height: 5rem;
  max-height: 24rem;
}
.togglecard{
  margin: 1rem;
  min-height: 32rem;
  min-width: 30rem;
  float: left;
  overflow: auto;
}
.p-card-header h3 {
    padding: 0.75rem;
}
.data, .databig {
  margin-top: 0.75rem;
}
.databig .p-fieldset-content {
  min-width: 24rem;
  max-height: 19rem;
  overflow: auto;
}
.datafield {
  margin-top: 0.75rem;
}
.datalabel {
  width: 5rem;
  display: inline-block;
}
.newdata {
  width: 4.75rem;
}
</style>

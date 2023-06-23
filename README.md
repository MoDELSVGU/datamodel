# datamodel
A parser component that parses JSON representation to Java object hierarchy for our datamodel.

Current support includes:
- Classes.
- Attributes with primitive types such as Integer and String.
- Association: binary associations.

Example:
```json
[
  {
    "class": "Lecturer",
    "attributes": [
      {
        "name": "name",
        "type": "String"
      },
      {
        "name": "age",
        "type": "Integer"
      }
    ],
    "ends": [
      {
        "association": "Enrollment",
        "name": "students",
        "target": "Student",
        "opp": "lecturers",
        "mult": "*"
      }
    ]
  },
  {
    "class": "Student",
    "attributes": [
      {
        "name": "name",
        "type": "String"
      },
      {
        "name": "age",
        "type": "Integer"
      }
    ],
    "ends": [
      {
        "association": "Enrollment",
        "name": "lecturers",
        "target": "Lecturer",
        "opp": "students",
        "mult": "*"
      }
    ]
  }
]
```

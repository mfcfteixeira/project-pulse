# Project Pulse

Project Pulse is a small backend tool I'm building to track and compare configuration differences between environments.  
It’s built with **Spring Boot**, **PostgreSQL**, and **Docker**, and it aims to make it easy to spot when two environments drift out of sync.

---

## What it does

Right now, Pulse can:

- Compare two environments and highlight any configuration differences  
- Detect missing or changed keys  
- Provide results through a simple REST API  
- Prepare the ground for a `.pulseignore` file (to skip expected differences)  

It’s still early in development, but the base structure is solid and ready to grow.

---

## Next steps

Implement .pulseignore logic

Add a simple web dashboard

Improve diff readability

Add change history and audit support

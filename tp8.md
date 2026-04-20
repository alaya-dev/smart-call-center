# Atelier 8 : Spring Data JPA - JPQL — Correction

⚠️ Toutes les fonctionnalités doivent être implémentées exclusivement à l’aide de requêtes JPQL dans les interfaces Repository, en utilisant l’annotation `@Query` de Spring Data JPA.

- L’utilisation des méthodes dérivées (findBy..., countBy...) est interdite.
- L’utilisation de requêtes SQL natives (nativeQuery = true) est interdite.
- Les requêtes doivent être écrites en utilisant les entités et leurs attributs.

---

## Q1. Récupérer la liste des appels affectés à un agent donné

```java
@Query("SELECT c FROM Calls c WHERE c.agent.id = :idAgent")
List<Calls> findCallsByAgent(@Param("idAgent") Long idAgent);
```

---

## Q2. Récupérer les appels nécessitant une compétence donnée

```java
@Query("SELECT c FROM Calls c JOIN c.requiredSkills s WHERE s = :skill")
List<Calls> findCallsBySkill(@Param("skill") Skills skill);
```

---

## Q3. Récupérer les agents possédant une compétence donnée

```java
@Query("SELECT a FROM Agent a JOIN a.skills s WHERE s = :skill")
List<Agent> findAgentsBySkill(@Param("skill") Skills skill);
```

---

## Q4. Récupérer l’agent le plus compétent et disponible pour un appel donné

```java
@Query("""
SELECT a FROM Agent a
WHERE a.available = true AND a.id IN (
  SELECT a2.id FROM Agent a2 JOIN a2.skills s
  WHERE a2.available = true AND a2.id IN (
    SELECT a3.id FROM Calls c JOIN c.requiredSkills rs JOIN Agent a3 JOIN a3.skills s2
    WHERE c.id = :callsId AND s2 MEMBER OF c.requiredSkills
  )
)
ORDER BY SIZE(
  (SELECT s FROM Agent a4 JOIN a4.skills s WHERE a4.id = a.id AND s MEMBER OF (SELECT rs2 FROM Calls c2 JOIN c2.requiredSkills rs2 WHERE c2.id = :callsId))
) DESC
""")
Agent findMostCompetentAgentForCall(@Param("callsId") Long callsId);
```

---

## Q5. Nombre d’appels par statut

```java
@Query("SELECT c.status, COUNT(c) FROM Calls c GROUP BY c.status")
List<Object[]> countCallsByStatus();
```

---

## Q6. Agents ayant traité plus de 5 appels

```java
@Query("SELECT a.name, COUNT(c) FROM Calls c JOIN c.agent a GROUP BY a.name HAVING COUNT(c) > 5")
List<Object[]> findTopActiveAgents();
```

---

## Q7. Récupérer les appels d’aujourd’hui

```java
@Query("SELECT c FROM Calls c WHERE FUNCTION('DATE', c.date) = CURRENT_DATE")
List<Calls> findTodayCalls();
```

---

**Remarques :**
- Adapter les noms d’entités et d’attributs selon votre modèle.
- Ajouter ces méthodes dans les interfaces Repository concernées.
- Utiliser `@Param` pour lier les paramètres.
- Tester chaque requête via Postman, Swagger, ou tests unitaires.


![image](https://github.com/yuheng-liu/SearchSuggestions/assets/42352679/7dc553f9-2c59-431c-9c0e-6a87a3354a24)

Requirements
1. The implementation must be native, written in Kotlin, and not using cross-platform
frameworks like Flutter, React Native etc.
2. You may use any third-party general-purpose libraries (e.g. Retrofit, Ktor, Moshi, Gson).
3. Primary objectives: robustness, code simplicity and maintainability.
4. Styles should be as close to the mock-up as possible.
5. The suggestions screen must be embedded in a fragment. The fragment must be able to
trigger events in the embedding activity to open search results in a browser.
6. The fragment must accept the initial search term as an (optional) argument.
7. A click on the back arrow ⬅️should close the search suggestions screen.
8. A click on the cross ❌should clear the search field.
9. A click on the diagonal arrow ↖️in the search suggestions list should put the
corresponding suggestion text into the search box.
10. A click on the 🔍in the soft keyboard should close the search suggestions screen and
pass the entered search term to the calling activity.
11. Suggestions must be fetched from a server during runtime, not embedded in the code.
12. For example, the following URL can be used:
https://duckduckgo.com/ac/?q={searchTerm}&type=list to fetch the list of suggestions.
13. When a user types some text, the list of suggestions must be updated. While fetching
the updated list of suggestions, the previous list must remain visible on the screen.

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

val supabase : SupabaseClient = createSupabaseClient(
    supabaseUrl = "https://emcavemxixgtihggvmto.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVtY2F2ZW14aXhndGloZ2d2bXRvIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTM3MjI5MDMsImV4cCI6MjAyOTI5ODkwM30.v9scDKMvM3k7aD3QGtgwgSqO7LsnMrd0pcVauV12hZk"
) {
    install(Auth)
    install(Postgrest)
    install(Realtime)

}